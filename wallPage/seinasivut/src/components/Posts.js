import React from "react";
import Likebutton from "./Likebutton";
import "./Posts.css";
import PropTypes from "prop-types";
import Comments from "./Comments";

const Posts = (props) => {

    const { filter, posts, userId, refetchPosts, show } = props;

    if (!posts || posts.length < 1) return <section>
        No posts yet...
    </section>;

    return <section>
        <h2>Posts</h2>
        <ul>
        {
        posts
            .filter(p => p.post.toLowerCase().includes(filter.toLowerCase())
                || p.user.username.toLowerCase().includes(filter.toLowerCase())
            )
            .map((p, i) =>
            <li key={i}>
                <h3>
                {
                `@ ${new Date(p.timestamp).toLocaleString()} ${p.user.username} says...`
                }
                </h3>
                <p>
                    {p.post}
                </p>
                <Likebutton p={p} userId={userId} refetchPosts={refetchPosts} show={show} />
                <Comments p={p} userId={userId} show={show} />
            </li>
            )
        }
        </ul>
    </section>
};

Posts.propTypes = {
    filter: PropTypes.string.isRequired,
    posts: PropTypes.array.isRequired,
    userId: PropTypes.string.isRequired,
    refetchPosts: PropTypes.func.isRequired,
    show: PropTypes.bool.isRequired
}

export default Posts;
