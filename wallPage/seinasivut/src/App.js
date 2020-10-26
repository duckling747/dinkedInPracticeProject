import React, { useEffect, useState } from 'react';
import Footer from './components/Footer';
import NavBar from './components/NavBar';
import PendingFriendRequests from './components/PendingFriendRequests';
import Posts from './components/Posts';
import { getCurrentUser, getUserWithId } from './services/user';

const App = () => {

  const urlParams = new URLSearchParams(window.location.search);
  const userId = urlParams.get("id");
  
  const [userOfWall, setUserOfWall] = useState({});
  const [currentUser, setCurrentUser] = useState("");

  
  useEffect(() => {
    getUserWithId(userId)
      .then(bod => setUserOfWall(bod));
    getCurrentUser()
        .then(bod => setCurrentUser(bod));
}, []);

  if (!userOfWall.username) return null;

  return (
    <div>
      <NavBar
        current={currentUser.username}
        username={userOfWall.username}
      />

      <h2>{`Wall of ${userOfWall.username}`}</h2>
      
      <PendingFriendRequests
        id={userId}
        username={userOfWall.username}
        current={currentUser.username}
      />

      <Posts />

      <Footer />

    </div>
    )
}

export default App;
