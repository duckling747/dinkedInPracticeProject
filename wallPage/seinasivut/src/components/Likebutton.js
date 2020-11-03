import React from "react";
import thumb from "../img/thumb.svg";
import { likePost } from "../services/user";
import PropTypes from "prop-types";

const Likebutton = ({p, refetchPosts, userId, show }) => {


    const sendLike = async (postId) => {
        console.log("liked!", postId);
        const res = await likePost(userId, postId)
        if (!res.ok) alert("problem with sending like");
        refetchPosts();
    };

    if(!show) return null;

    return (
        <>
            <a href="#" onClick={() => sendLike(p.id)}>
                <img id="thumbimage" src={thumb} alt="thumbs up"></img>
            </a>
            <label htmlFor="thumbimage">
                &nbsp;{p.likes.length}
            </label>
        </>
    )
}

Likebutton.propTypes = {
    p: PropTypes.object.isRequired,
    refetchPosts: PropTypes.func.isRequired,
    userId: PropTypes.string.isRequired,
    show: PropTypes.bool.isRequired
};

export default Likebutton;
