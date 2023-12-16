// import logo from './logo.svg';
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import { BrowserRouter as Router, Route, Routes, Navigate, Outlet } from 'react-router-dom';
import React, { useState } from 'react';

import "./components/news/List.css"

import Login from './components/users/Login';

import EditNews from './components/news/Edit';
import AddNews from './components/news/Add';
import ListNews from './components/news/List';

import Index from './components/Index';

import EditTopic from './components/topic/Edit';
import AddTopic from './components/topic/Add';
import ListTopic from './components/topic/List';
import ResetPassword from './components/users/ResetPassword';
import LeftNav from './components/LeftNav';



function App() {

  const getUserFromStorage = () => {
    const _user = localStorage.getItem('user');
    if (_user) {
      return JSON.parse(_user)
    }
    return null;
  }

  const setUserToStorage = (_user) => {
    if (!_user) {
      localStorage.removeItem('user');
      setUser(null);
      return;
    }
    if (_user) { localStorage.setItem('user', JSON.stringify(_user)) };
    setUser(_user);
  }

  // bảo vệ trang cần Login
  const ProtectedLayout = () => {
    if (!user) {
      return <Navigate to="/login" />;
    }
    return (
      <Outlet />
    )
  }

  // các trang không cần Login
  const UnrotectedLayout = () => {
    if (user) {
      return <Navigate to="/" />;
    }
    return (
      <Outlet />
    )
  }

  const logout = () => {
    setUser(null);
  }

  const [user, setUser] = useState(getUserFromStorage());
  console.log(user);

  return (
    <Router>
      <div className="App">
        <Routes>
          <Route path="/reset-password" element={<ResetPassword  />} />
          <Route element={<UnrotectedLayout />} >
            <Route path="/login" element={<Login user={user} setUser={setUserToStorage} />} />
          </Route>

          <Route element={<ProtectedLayout />} >
            <Route path="/" element={<Index user={user} logout={logout} />} />

            <Route path="/news" element={<ListNews user={user} setUser={setUserToStorage} logout={logout} />} />
            <Route path="/news/add" element={<AddNews user={user} />} logout={logout} />
            <Route path="/news/update/:id" element={<EditNews user={user} logout={logout} />} />

            <Route path="/topic" element={<ListTopic user={user} setUser={setUserToStorage} logout={logout} />} />
            <Route path="/topic/add" element={<AddTopic user={user} />} logout={logout} />
            <Route path="/topic/update/:id" element={<EditTopic user={user} logout={logout} />} />
          </Route>

        </Routes>
      </div>
    </Router>

  );
  /*  cd web-admin
      npm start     */
}

export default App;
