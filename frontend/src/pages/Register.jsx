import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { Activity } from 'lucide-react';

const Register = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    phone: '',
    role: 'PATIENT',
    // Patient specific
    dateOfBirth: '',
    gender: 'MALE',
    bloodGroup: '',
    address: '',
    // Doctor specific
    specialization: '',
    experienceYears: '',
    qualification: '',
    department: 'General'
  });
  
  const [error, setError] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  
  const { register } = useAuth();
  const navigate = useNavigate();

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setIsLoading(true);
    
    // Convert experience to number if doctor
    const dataToSubmit = { ...formData };
    if (dataToSubmit.role === 'DOCTOR' && dataToSubmit.experienceYears) {
      dataToSubmit.experienceYears = parseInt(dataToSubmit.experienceYears, 10);
    }
    
    const result = await register(dataToSubmit);
    
    if (result.success) {
      navigate('/login', { state: { message: 'Registration successful! Please login.' } });
    } else {
      setError(result.error);
    }
    setIsLoading(false);
  };

  return (
    <div className="min-h-screen py-10 bg-gray-50 flex flex-col justify-center sm:py-12">
      <div className="relative py-3 sm:max-w-xl sm:mx-auto w-full px-4 sm:px-0">
        <div className="relative px-4 py-10 bg-white mx-8 md:mx-0 shadow-lg rounded-3xl sm:p-10 border border-gray-100">
          
          <div className="max-w-md mx-auto">
            <div className="flex items-center space-x-5 mb-6">
              <div className="h-12 w-12 bg-primary-100 rounded-full flex flex-shrink-0 justify-center items-center text-primary-500">
                <Activity size={24} />
              </div>
              <div className="block font-semibold text-xl self-start text-gray-700">
                <h2 className="leading-relaxed">Create an Account</h2>
                <p className="text-sm text-gray-500 font-normal leading-relaxed">Join the Telemedicine Kiosk</p>
              </div>
            </div>
            
            <form onSubmit={handleSubmit} className="divide-y divide-gray-200">
              {error && (
                <div className="mb-4 bg-red-50 text-red-500 p-3 rounded-md text-sm">
                  {error}
                </div>
              )}
              
              <div className="py-4 text-base leading-6 space-y-4 text-gray-700 sm:text-lg sm:leading-7">
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700">First Name</label>
                    <input type="text" name="firstName" required value={formData.firstName} onChange={handleChange}
                      className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700">Last Name</label>
                    <input type="text" name="lastName" required value={formData.lastName} onChange={handleChange}
                      className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                  </div>
                </div>
                
                <div className="grid grid-cols-2 gap-4">
                  <div>
                    <label className="block text-sm font-medium text-gray-700">Email Address</label>
                    <input type="email" name="email" required value={formData.email} onChange={handleChange}
                      className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                  </div>
                  <div>
                    <label className="block text-sm font-medium text-gray-700">Password</label>
                    <input type="password" name="password" required value={formData.password} onChange={handleChange}
                      className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                  </div>
                </div>

                <div>
                  <label className="block text-sm font-medium text-gray-700">Role</label>
                  <select name="role" value={formData.role} onChange={handleChange}
                    className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm rounded-md bg-white border">
                    <option value="PATIENT">Patient</option>
                    <option value="DOCTOR">Doctor</option>
                  </select>
                </div>

                {/* Conditional Fields Based on Role */}
                {formData.role === 'PATIENT' && (
                  <div className="space-y-4 pt-2 border-t border-gray-100">
                    <h4 className="text-sm font-semibold text-gray-500 uppercase tracking-wider">Patient Details</h4>
                    <div className="grid grid-cols-2 gap-4">
                       <div>
                        <label className="block text-sm font-medium text-gray-700">Date of Birth</label>
                        <input type="date" name="dateOfBirth" required value={formData.dateOfBirth} onChange={handleChange}
                          className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                      </div>
                      <div>
                        <label className="block text-sm font-medium text-gray-700">Gender</label>
                        <select name="gender" value={formData.gender} onChange={handleChange}
                          className="mt-1 block w-full pl-3 pr-10 py-2 text-base border-gray-300 focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm rounded-md bg-white border">
                          <option value="MALE">Male</option>
                          <option value="FEMALE">Female</option>
                          <option value="OTHER">Other</option>
                        </select>
                      </div>
                    </div>
                  </div>
                )}

                {formData.role === 'DOCTOR' && (
                  <div className="space-y-4 pt-2 border-t border-gray-100">
                    <h4 className="text-sm font-semibold text-gray-500 uppercase tracking-wider">Doctor Details</h4>
                    <div className="grid grid-cols-2 gap-4">
                       <div>
                        <label className="block text-sm font-medium text-gray-700">Specialization</label>
                        <input type="text" name="specialization" required={formData.role === 'DOCTOR'} value={formData.specialization} onChange={handleChange} placeholder="e.g. Cardiologist"
                          className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                      </div>
                      <div>
                        <label className="block text-sm font-medium text-gray-700">Experience (Years)</label>
                        <input type="number" name="experienceYears" required={formData.role === 'DOCTOR'} value={formData.experienceYears} onChange={handleChange}
                          className="mt-1 block w-full px-3 py-2 bg-white border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500 sm:text-sm" />
                      </div>
                    </div>
                  </div>
                )}
                
              </div>
              <div className="pt-4 flex items-center space-x-4">
                <button type="submit" disabled={isLoading}
                  className={`bg-primary-600 flex justify-center items-center w-full text-white px-4 py-3 rounded-md focus:outline-none hover:bg-primary-700 transition font-medium ${isLoading ? 'opacity-70 cursor-not-allowed' : ''}`}>
                  {isLoading ? 'Creating Account...' : 'Register'}
                </button>
              </div>
              
              <div className="pt-4 text-center text-sm">
                <span className="text-gray-600">Already have an account? </span>
                <Link to="/login" className="font-medium text-primary-600 hover:text-primary-500">
                  Sign in
                </Link>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Register;
