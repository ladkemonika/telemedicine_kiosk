import { Users, Activity, LogOut } from 'lucide-react';
import { useAuth } from '../context/AuthContext';

const AdminDashboard = () => {
  const { user } = useAuth();
  
  return (
    <div className="space-y-6">
      <div className="bg-gray-800 p-6 rounded-xl shadow-md text-white flex justify-between items-center">
        <div>
          <h1 className="text-2xl font-bold flex items-center">
            <Activity className="h-6 w-6 mr-3 text-primary-400" /> 
            Admin Control Panel
          </h1>
          <p className="text-gray-300 mt-1">Manage system users, doctors, and application settings</p>
        </div>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <div className="bg-white p-6 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow cursor-pointer">
            <h3 className="font-semibold text-lg text-gray-900 flex items-center mb-4 border-b pb-2">
                <Users className="h-5 w-5 mr-2 text-blue-500" />
                Manage Doctors
            </h3>
            <p className="text-gray-500 text-sm mb-4">Add, remove, or verify doctor profiles in the system.</p>
            <button className="text-blue-600 font-medium text-sm hover:underline">View All Doctors &rarr;</button>
        </div>

        <div className="bg-white p-6 rounded-xl border border-gray-100 shadow-sm hover:shadow-md transition-shadow cursor-pointer">
            <h3 className="font-semibold text-lg text-gray-900 flex items-center mb-4 border-b pb-2">
                <Users className="h-5 w-5 mr-2 text-green-500" />
                Manage Patients
            </h3>
            <p className="text-gray-500 text-sm mb-4">View patient records and handle support requests.</p>
            <button className="text-green-600 font-medium text-sm hover:underline">View All Patients &rarr;</button>
        </div>
      </div>
    </div>
  );
};

export default AdminDashboard;
