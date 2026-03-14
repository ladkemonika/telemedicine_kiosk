import { useAuth } from '../context/AuthContext';
import PatientDashboard from './PatientDashboard';
import DoctorDashboard from './DoctorDashboard';
import AdminDashboard from './AdminDashboard';
import { Activity } from 'lucide-react';

const DashboardRouter = () => {
    const { user } = useAuth();

    if (!user) return <div className="p-8 flex justify-center"><Activity className="animate-spin h-8 w-8 text-primary-500" /></div>;

    switch (user.role) {
        case 'PATIENT':
            return <PatientDashboard />;
        case 'DOCTOR':
            return <DoctorDashboard />;
        case 'ADMIN':
            return <AdminDashboard />;
        default:
            return (
                <div className="p-8 text-center bg-white shadow-sm rounded-xl border border-red-100">
                    <h2 className="text-xl text-red-600 font-bold mb-2">Access Denied</h2>
                    <p className="text-gray-600">Your role doesn't have a configured dashboard view.</p>
                </div>
            )
    }
}

export default DashboardRouter;
