import React, { useEffect, useState } from 'react';
import Footer from './components/Footer';
import NavBar from './components/NavBar';
import PendingFriendRequests from './components/PendingFriendRequests';
import Posts from './components/Posts';
import WritePostBox from './components/WritePostBox';
import { getCurrentUser, getPendingFriendRequests, getUserAndFriendsPosts, getUserWithId } from './services/user';

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

  return (
    <div>
      <NavBar
        current={currentUser.username}
        username={userOfWall.username}
        setFilter={setFilter}
      />

      <h2>{`Wall of ${userOfWall.username}`}</h2>
      
      <PendingFriendRequests
        refetchFriendReqs={refetchFriendReqs}
        refetchPosts={refetchPosts}
        pendingList={pendingList}
        username={userOfWall.username}
        current={currentUser.username}
      />

      <Posts userId={userId}
        filter={filter}
        posts={posts}
      />

      <WritePostBox />

      <Footer />

    </div>
    )
}

export default App;
