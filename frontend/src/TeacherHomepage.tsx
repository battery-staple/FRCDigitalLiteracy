import React from 'react';
import {Button, Card} from "react-bootstrap";

interface LessonCardData {
    name: string
    description: string
    url: string
    progress: number
}

export default class TeacherHomepage extends React.Component<{ cards: LessonCardData[] }, any> {
    render() {
        return (<div>
            <h1 className="TeacherHomepage-header">Unplayed</h1>
            <div className="TeacherHomepage-card-row">
                {
                    this.props.cards
                        .filter((data) => data.progress <= 0.999)
                        .map(LessonCard)
                }
            </div>
            <h1 className="TeacherHomepage-header">Completed</h1>
            <div className="TeacherHomepage-card-row">
                {
                    this.props.cards
                        .filter((data) => data.progress > 0.999)
                        .map(LessonCard)
                }
            </div>
        </div>);
    }
}

function LessonCard(cardData: LessonCardData, index: number): JSX.Element {
    return <div className="TeacherHomepage-card" key={index.toString()}>
        <Card>
            <Card.Body>
                <Card.Title>{cardData.name}</Card.Title>
                <Card.Text>{cardData.description}</Card.Text>
                <Button variant="primary" href={cardData.url}>Start</Button>
            </Card.Body>
            <div className="TeacherHomepage-card-progress" style={{width: `${cardData.progress*100}%`}}/>
        </Card>
    </div>
}