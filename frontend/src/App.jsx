import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { ProtectedRoute } from './components/ProtectedRoute';
import Layout from './components/Layout';

// Pages
import Login from './pages/Login';
import Register from './pages/Register';
import DashboardRouter from './pages/DashboardRouter'; // We'll expand this later for dynamic routing

function App() {
  return (
    <AuthProvider>
      <Router>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          
          {/* Protected Routes inside Layout */}
          <Route element={<Layout />}>
            <Route element={<ProtectedRoute />}>
              <Route path="/dashboard" element={<DashboardRouter />} />
              {/* Other specific routes will go here (doctors, appointments, etc.) */}
            </Route>
            
            {/* Catch-all redirect */}
            <Route path="*" element={<Navigate to="/dashboard" replace />} />
          </Route>
        </Routes>
      </Router>
    </AuthProvider>
  );
}

export default App;
