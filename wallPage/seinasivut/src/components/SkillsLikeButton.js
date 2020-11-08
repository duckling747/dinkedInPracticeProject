import React from "react";
import thumb from "../img/thumb.svg";
import { likeSkill } from "../services/user";
import PropTypes from "prop-types";

const SkillsLikebutton = ({ s, refetchSkills, userId, show }) => {


    const sendLike = async (skillId) => {
        console.log("liked!", skillId);
        const res = await likeSkill(skillId, userId);
        if (!res.ok) alert("problem with sending like");
        refetchSkills();
    };

    if(!show) return null;

    return (
        <>
            <a href="#" onClick={() => sendLike(s.id)}>
                <img id="thumbimage" src={thumb} alt="thumbs up"></img>
            </a>
            <label htmlFor="thumbimage">
                &nbsp;{s.likes.length}
            </label>
        </>
    )
}

SkillsLikebutton.propTypes = {
    s: PropTypes.object.isRequired,
    refetchSkills: PropTypes.func.isRequired,
    userId: PropTypes.string.isRequired,
    show: PropTypes.bool.isRequired
};

export default SkillsLikebutton;
