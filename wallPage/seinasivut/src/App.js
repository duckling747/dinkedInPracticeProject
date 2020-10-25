import React, { useEffect, useState } from 'react';
import NavBar from './components/NavBar';
import PendingFriendRequests from './components/PendingFriendRequests';
import { getUserWithId } from './services/user';

const App = () => {

  const urlParams = new URLSearchParams(window.location.search);
  const userId = urlParams.get("id");

  const [user, setUser] = useState({});


  useEffect(() => {
    getUserWithId(userId)
      .then(bod => {
        console.log(bod);
        setUser(bod);
      })
  }, [userId]);

  if (!user) return null;

  return (
    <div>
      <NavBar />

      <h2>{`Wall of ${user.username}`}</h2>
      
      <PendingFriendRequests id={userId} />

    </div>
    )
}

export default App;
