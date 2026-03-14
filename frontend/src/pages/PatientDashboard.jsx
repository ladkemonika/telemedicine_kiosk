import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';
import { Calendar, Clock, FileText, User as UserIcon } from 'lucide-react';

const PatientDashboard = () => {
  const { user } = useAuth();
  const [appointments, setAppointments] = useState([]);
  const [doctors, setDoctors] = useState([]);
  const [records, setRecords] = useState([]);
  const [loading, setLoading] = useState(true);
  const [activeTab, setActiveTab] = useState('appointments'); // appointments, book, records

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const [apptsRes, docsRes, recsRes] = await Promise.all([
          api.get('/appointments/patient'),
          api.get('/doctors'),
          user.role === 'PATIENT' ? api.get(`/records/patient/${user.id}`) : Promise.resolve({ data: [] }) // Adjust ID logic properly in prod
        ]);
        
        setAppointments(apptsRes.data);
        setDoctors(docsRes.data);
        setRecords(recsRes.data);
      } catch (error) {
        console.error("Error fetching patient dashboard data", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, [user]);

  if (loading) return <div className="p-8 text-center animate-pulse text-gray-500">Loading dashboard...</div>;

  return (
    <div className="space-y-6">
      <div className="bg-white p-6 rounded-xl shadow-sm border border-gray-100 flex items-center justify-between">
        <div>
          <h1 className="text-2xl font-bold text-gray-900">Patient Dashboard</h1>
          <p className="text-gray-500 mt-1">Manage your appointments and medical records</p>
        </div>
        <div className="h-16 w-16 bg-primary-100 rounded-full flex items-center justify-center text-primary-600">
          <UserIcon size={32} />
        </div>
      </div>

      <div className="flex space-x-4 border-b border-gray-200">
        <button 
          className={`pb-4 px-2 font-medium text-sm transition-colors relative ${activeTab === 'appointments' ? 'text-primary-600' : 'text-gray-500 hover:text-gray-700'}`}
          onClick={() => setActiveTab('appointments')}
        >
          My Appointments
          {activeTab === 'appointments' && <span className="absolute bottom-0 left-0 w-full h-0.5 bg-primary-600 rounded-t-full"></span>}
        </button>
        <button 
          className={`pb-4 px-2 font-medium text-sm transition-colors relative ${activeTab === 'book' ? 'text-primary-600' : 'text-gray-500 hover:text-gray-700'}`}
          onClick={() => setActiveTab('book')}
        >
          Book Appointment
          {activeTab === 'book' && <span className="absolute bottom-0 left-0 w-full h-0.5 bg-primary-600 rounded-t-full"></span>}
        </button>
        <button 
          className={`pb-4 px-2 font-medium text-sm transition-colors relative ${activeTab === 'records' ? 'text-primary-600' : 'text-gray-500 hover:text-gray-700'}`}
          onClick={() => setActiveTab('records')}
        >
          Medical Records
          {activeTab === 'records' && <span className="absolute bottom-0 left-0 w-full h-0.5 bg-primary-600 rounded-t-full"></span>}
        </button>
      </div>

      {activeTab === 'appointments' && (
        <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-3">
          {appointments.length === 0 ? (
            <div className="col-span-full p-8 text-center bg-white rounded-xl border border-dashed border-gray-300">
              <Calendar className="mx-auto h-12 w-12 text-gray-400 mb-3" />
              <p className="text-gray-500">No appointments scheduled.</p>
              <button 
                onClick={() => setActiveTab('book')}
                className="mt-4 text-primary-600 font-medium hover:text-primary-700"
              >
                Book your first appointment
              </button>
            </div>
          ) : (
            appointments.map(appt => (
              <div key={appt.id} className="bg-white p-5 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow">
                <div className="flex justify-between items-start mb-4">
                  <div>
                    <h3 className="font-semibold text-gray-900">Dr. {appt.doctor.user.lastName}</h3>
                    <p className="text-sm text-gray-500">{appt.doctor.specialization}</p>
                  </div>
                  <span className={`px-2.5 py-1 text-xs font-medium rounded-full ${
                    appt.status === 'SCHEDULED' ? 'bg-blue-100 text-blue-800' : 
                    appt.status === 'COMPLETED' ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'
                  }`}>
                    {appt.status}
                  </span>
                </div>
                <div className="space-y-2 text-sm text-gray-600">
                  <div className="flex items-center">
                    <Calendar className="h-4 w-4 mr-2" />
                    {new Date(appt.appointmentDate).toLocaleDateString()}
                  </div>
                  <div className="flex items-center">
                    <Clock className="h-4 w-4 mr-2" />
                    {new Date(appt.appointmentDate).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                  </div>
                </div>
              </div>
            ))
          )}
        </div>
      )}

      {activeTab === 'book' && (
        <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
          {doctors.map(doctor => (
            <div key={doctor.id} className="bg-white p-6 rounded-xl border border-gray-100 shadow-sm flex flex-col">
              <div className="flex items-center mb-4">
                <div className="h-12 w-12 bg-indigo-100 rounded-full flex items-center justify-center text-indigo-600 font-bold text-lg">
                  {doctor.user.lastName.charAt(0)}
                </div>
                <div className="ml-4 flex-1">
                  <h3 className="font-semibold text-gray-900">Dr. {doctor.user.firstName} {doctor.user.lastName}</h3>
                  <p className="text-sm text-primary-600 font-medium">{doctor.specialization}</p>
                </div>
              </div>
              <p className="text-sm text-gray-600 mb-4 flex-grow line-clamp-2">
                {doctor.experienceYears} Years Experience • {doctor.department}
              </p>
              <button 
                className="w-full bg-primary-50 text-primary-700 py-2 rounded-lg font-medium hover:bg-primary-100 transition-colors"
                onClick={() => {/* Implement booking modal/form logic here */}}
              >
                Book Appointment
              </button>
            </div>
          ))}
        </div>
      )}

      {activeTab === 'records' && (
        <div className="bg-white rounded-xl shadow-sm border border-gray-100 overflow-hidden">
           {records.length === 0 ? (
             <div className="p-8 text-center text-gray-500">
               <FileText className="mx-auto h-12 w-12 text-gray-400 mb-3" />
               No medical records found.
             </div>
           ) : (
             <ul className="divide-y divide-gray-200">
               {records.map(record => (
                 <li key={record.id} className="p-6 hover:bg-gray-50 transition-colors">
                   <div className="flex items-center justify-between">
                     <div>
                       <h4 className="text-sm font-medium text-gray-900">{record.title}</h4>
                       <p className="text-sm text-gray-500 mt-1">{record.description}</p>
                     </div>
                     <span className="text-xs text-gray-400">
                       {new Date(record.uploadedAt).toLocaleDateString()}
                     </span>
                   </div>
                 </li>
               ))}
             </ul>
           )}
        </div>
      )}
    </div>
  );
};

export default PatientDashboard;
