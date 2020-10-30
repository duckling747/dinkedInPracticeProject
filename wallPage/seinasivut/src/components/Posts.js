import React from "react";
import "./Posts.css";

const Posts = (props) => {

    const { filter, posts } = props;

    if (!posts || posts.length < 1) return <section>
        No posts yet...
    </section>;

    console.log("postsList in Posts", posts);

    return <section>
        <h3>Posts</h3>
        <ul>
        {
        posts
            .filter(p => p.post.toLowerCase().includes(filter.toLowerCase())
                || p.user.username.toLowerCase().includes(filter.toLowerCase())
            )
            .map((p, i) =>
            <li key={i}>
                <h4>
                {
                `@ ${new Date(p.timestamp).toLocaleString()} ${p.user.username} says...`
                }
                </h4>
                {p.post}
            </li>
            )
        }
        </ul>
    </section>
};

export default Posts;
