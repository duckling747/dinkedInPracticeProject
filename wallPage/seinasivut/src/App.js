import React, { useEffect, useState } from 'react';
import Footer from './components/Footer';
import NavBar from './components/NavBar';
import PendingFriendRequests from './components/PendingFriendRequests';
import Posts from './components/Posts';
import WritePostBox from './components/WritePostBox';
import {
  getCurrentUser, getPendingFriendRequests, getUserAndFriendsPosts, getUserWithId
} from './services/user';
import defaultImg from "./img/default-profile.png"
import "./App.css";

const App = () => {

  const urlParams = new URLSearchParams(window.location.search);
  const userId = urlParams.get("id");
  
  const [userOfWall, setUserOfWall] = useState({});
  const [currentUser, setCurrentUser] = useState("");
  const [filter, setFilter] = useState("");
  const [pendingList, setPendingList] = useState([]);
  const [posts, setPosts] = useState([]);

  console.log("userid", userId);


  const refetchFriendReqs = async () => {
    const friendReqs = await getPendingFriendRequests(userId);
    console.log("pending reqs:", friendReqs);
    setPendingList([...friendReqs]);
  };

  const refetchPosts = async () => {
    const posts = await getUserAndFriendsPosts(userId);
    console.log("posts:", posts);
    setPosts([...posts]);
  };

  useEffect(() => {
    getUserWithId(userId)
      .then(bod => setUserOfWall(bod));
    getCurrentUser()
        .then(bod => setCurrentUser(bod));
    getPendingFriendRequests(userId)
      .then(bod => setPendingList([...bod]));
    getUserAndFriendsPosts(userId)
      .then(bod => setPosts([...bod]));
}, [userId]);

  if (!userOfWall.username) return null;

  const currentUserIsUserOfWall = currentUser.username === userOfWall.username;

  return (
    <div>
      <NavBar
        show={currentUserIsUserOfWall}
        setFilter={setFilter}
      />

      <h2>
        {
      `Wall of ${userOfWall.username} ${currentUserIsUserOfWall 
        ? "(that's you)"
        : ""}`
        }
      </h2>

      <object className="imgstuff" data={`/accounts/${userOfWall.id}/image`} type="image/png">
        <img className="imgstuff" src={defaultImg} alt="default image"></img>
      </object>
      
      <PendingFriendRequests
        refetchFriendReqs={refetchFriendReqs}
        refetchPosts={refetchPosts}
        pendingList={pendingList}
        current={currentUser.username}
        show={currentUserIsUserOfWall}
      />

      <Posts userId={userId}
        refetchPosts={refetchPosts}
        filter={filter}
        posts={posts}
        show={currentUserIsUserOfWall}
      />

      <WritePostBox
        id={userId}
        refetchPosts={refetchPosts}
        show={currentUserIsUserOfWall}    
      />

      <Footer />

    </div>
    )
}

export default App;
