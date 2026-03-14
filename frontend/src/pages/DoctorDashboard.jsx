import { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';
import { Calendar, Users, Activity, FileText } from 'lucide-react';

const DoctorDashboard = () => {
  const { user } = useAuth();
  const [appointments, setAppointments] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchDashboardData = async () => {
      try {
        const response = await api.get('/appointments/doctor');
        setAppointments(response.data);
      } catch (error) {
        console.error("Error fetching doctor dashboard data", error);
      } finally {
        setLoading(false);
      }
    };

    fetchDashboardData();
  }, [user]);

  if (loading) return <div className="p-8 text-center animate-pulse text-gray-500">Loading schedule...</div>;

  const todayAppointments = appointments.filter(a => {
      const apptDate = new Date(a.appointmentDate);
      const today = new Date();
      return apptDate.getDate() === today.getDate() &&
             apptDate.getMonth() === today.getMonth() &&
             apptDate.getFullYear() === today.getFullYear();
  });

  return (
    <div className="space-y-6">
      
      {/* Stats Overview */}
      <div className="grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
        <div className="bg-white overflow-hidden shadow-sm rounded-xl border border-gray-100">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-blue-50 rounded-md p-3">
                <Calendar className="h-6 w-6 text-blue-600" aria-hidden="true" />
              </div>
              <div className="ml-5 w-0 flex-1 flex flex-col">
                <dt className="text-sm font-medium text-gray-500 truncate">Today's Appointments</dt>
                <dd className="text-2xl font-bold text-gray-900">{todayAppointments.length}</dd>
              </div>
            </div>
          </div>
        </div>

        <div className="bg-white overflow-hidden shadow-sm rounded-xl border border-gray-100">
          <div className="p-5">
            <div className="flex items-center">
              <div className="flex-shrink-0 bg-green-50 rounded-md p-3">
                <Users className="h-6 w-6 text-green-600" aria-hidden="true" />
              </div>
              <div className="ml-5 w-0 flex-1 flex flex-col">
                <dt className="text-sm font-medium text-gray-500 truncate">Total Patients</dt>
                {/* Simplified stat for MVP */}
                <dd className="text-2xl font-bold text-gray-900">{new Set(appointments.map(a => a.patient.id)).size}</dd>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="bg-white shadow-sm rounded-xl border border-gray-100 overflow-hidden">
        <div className="px-6 py-5 border-b border-gray-200">
          <h3 className="text-lg leading-6 font-medium text-gray-900">Upcoming Schedule</h3>
        </div>
        
        {appointments.length === 0 ? (
            <div className="p-8 text-center text-gray-500">
                <Activity className="mx-auto h-12 w-12 text-gray-300 mb-4" />
                No appointments scheduled currently.
            </div>
        ) : (
          <ul className="divide-y divide-gray-200 bg-white">
            {appointments.map((appointment) => (
              <li key={appointment.id} className="hover:bg-gray-50 transition-colors">
                <div className="px-6 py-4 flex items-center justify-between">
                  <div className="flex items-center">
                    <div className="h-10 w-10 flex-shrink-0 rounded-full bg-gray-200 flex items-center justify-center font-bold text-gray-600">
                        {appointment.patient.user.lastName.charAt(0)}
                    </div>
                    <div className="ml-4">
                      <div className="text-sm font-medium text-gray-900">
                        {appointment.patient.user.firstName} {appointment.patient.user.lastName}
                      </div>
                      <div className="text-sm text-gray-500">
                        {appointment.notes || 'Routine checkup'}
                      </div>
                    </div>
                  </div>
                  
                  <div className="flex flex-col items-end">
                    <div className="text-sm text-gray-900 font-medium font-mono bg-gray-100 px-2 rounded">
                        {new Date(appointment.appointmentDate).toLocaleTimeString([], {hour: '2-digit', minute:'2-digit'})}
                    </div>
                    <div className="text-xs text-gray-500 mt-1">
                        {new Date(appointment.appointmentDate).toLocaleDateString()}
                    </div>
                  </div>
                  
                  <div className="ml-6 flex items-center space-x-3">
                    <button className="text-primary-600 hover:text-primary-900 p-2 rounded-full hover:bg-primary-50 transition-colors" title="Write Prescription">
                        <FileText size={20} />
                    </button>
                    {/* Add Chat icon trigger here */}
                  </div>
                </div>
              </li>
            ))}
          </ul>
        )}
      </div>
    </div>
  );
};

export default DoctorDashboard;
