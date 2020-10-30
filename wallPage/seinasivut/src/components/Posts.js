import React from "react";
import Likebutton from "./Likebutton";
import "./Posts.css";
import PropTypes from "prop-types";

const Posts = (props) => {

    const { filter, posts, userId, refetchPosts, show } = props;

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
                <p>
                    {p.post}
                </p>
                <Likebutton p={p} userId={userId} refetchPosts={refetchPosts} show={show} />
            </li>
            )
        }
        </ul>
    </section>
};

Posts.propTypes = {
    filter: PropTypes.string.isRequired,
    posts: PropTypes.array.isRequired,
    userId: PropTypes.number.isRequired,
    refetchPosts: PropTypes.func.isRequired,
    show: PropTypes.bool.isRequired
}

export default Posts;
