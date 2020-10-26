import React, { useEffect, useState } from "react";
import { getUserAndFriendsPosts } from "../services/user";

const Posts = (props) => {

    const { userId, filter } = props;

    const [postsList, setPostsList] = useState([]);

    useEffect(() => {
        getUserAndFriendsPosts(userId)
            .then(bod => setPostsList(bod));
    }, []);

    if (!postsList || postsList.length < 1) return null;

    console.log(postsList);

    return <section>
        <h3>Posts</h3>
        <ul>
            {
                postsList
                    .filter(p => p.post.toLowerCase().includes(filter.toLowerCase())
                        || p.user.username.toLowerCase().includes(filter.toLowerCase())
                    )
                    .map((p, i) =>
                    <li key={i}>
                        <h4>{`@ ${new Date(p.timestamp).toLocaleString()} ${p.user.username} says...`}</h4>
                        {p.post}
                    </li>
                    )
            }
        </ul>
    </section>
};

export default Posts;
