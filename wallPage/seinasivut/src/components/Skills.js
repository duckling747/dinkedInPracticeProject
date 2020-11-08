import React from "react";
import SkillLikeButton from "./SkillsLikeButton";
import "./Skills.css";

const Skills = ({ userId, skills, refetchSkills, show }) => {

    console.log("skills", skills);

    if (!skills || skills.length < 1) return <section>no skills yet...</section>;
    
    return (
        <section>
            <h2>
                Skills
            </h2>
            <h3>Top skills:</h3>
            <ul id="topskills">
                {
                skills
                .slice(0, 3)
                .map((s,i) =>
                <li key={i}>
                    <strong>
                    {s.title}
                    </strong>
                    <div>{s.description}</div>
                    <SkillLikeButton s={s} refetchSkills={refetchSkills} userId={userId} show={show} />
                </li>)
                }
            </ul>
            <h3>Other skills (if there are any):</h3>
            <ul>
                {
                skills
                .slice(3)
                .map((s,i) =>
                <li key={i}>
                    <strong>
                    {s.title}
                    </strong>
                    <div>{s.description}</div>
                    <SkillLikeButton s={s} refetchSkills={refetchSkills} userId={userId} show={show} />
                </li>)
                }
            </ul>
        </section>
    );
};

export default Skills;
