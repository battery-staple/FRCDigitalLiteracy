import React from 'react';
import {Button, Card, Container, Navbar} from "react-bootstrap";
import GoogleLogin, {
    GoogleLoginResponse,
    GoogleLoginResponseOffline,
    GoogleLogout
} from "react-google-login";
import jwtDecode from "jwt-decode";

interface LessonCardData {
    name: string
    description: string
    url: string
    progress: number
}

interface TeacherHomepageProps {
    cards: LessonCardData[]
}

export default class TeacherHomepage extends React.Component<TeacherHomepageProps, { profile: { token: string, pfpURL: string } | null }> {
    constructor(props: any) {
        console.log("loaded homepage!");
        super(props);
        this.state = { profile: null };
    }

    componentDidMount() {
        const loginToken = localStorage.getItem("loginToken");
        if (loginToken !== null) {
            new Promise<{ token: string, pfpURL: string }>((resolve, reject) => {
                const decodedJWT: any = jwtDecode(loginToken)
                if ("picture" in decodedJWT) {
                    console.log(decodedJWT);
                    resolve({
                        token: loginToken,
                        pfpURL: decodedJWT.picture
                    })
                } else {
                    reject()
                }
            })
                .then(profile => {
                    this.setState({
                        profile: profile
                    })
                })
        }
    }

    componentDidUpdate(prevProps: Readonly<TeacherHomepageProps>, prevState: Readonly<{ profile: { token: string; pfpURL: string } | null }>, snapshot?: any) {
        if (prevState.profile?.token !== this.state.profile?.token) {
            if (this.state.profile === null) {
                localStorage.removeItem("loginToken")
            } else {
                localStorage.setItem("loginToken", this.state.profile.token)
            }
        }
    }

    render() {
        return (<div>
            <Navbar bg="light">
                <Container>
                    <Navbar.Brand href="/">Digital Literacy</Navbar.Brand>
                    <Navbar.Toggle aria-controls="basic-navbar-nav" />
                    <LoginButton
                        signedIn={ this.state.profile !== null }
                        pfpURL={ this.state.profile?.pfpURL ?? null }
                        onSuccess={ response => {
                            console.log(response);
                            if ("tokenId" in response && "profileObj" in response) {
                                this.setState({
                                    profile: {
                                        token: response.tokenId,
                                        pfpURL: response.profileObj.imageUrl
                                    }
                                })
                            }
                        }}
                        onLoginFailure={ response => {
                            console.log(response);
                        }}
                        onLogoutSuccess= { () => {
                            this.setState({
                                profile: null
                            })
                        }}
                    />
                </Container>
            </Navbar>
            <Container>
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
            </Container>
        </div>);
    }
}

function LoginButton(props: {
    signedIn: boolean,
    pfpURL: string | null
    readonly onSuccess?: (response: GoogleLoginResponse | GoogleLoginResponseOffline) => void,
    readonly onLoginFailure?: (error: any) => void,
    readonly onLogoutSuccess?: () => void;
    readonly onLogoutFailure?: () => void;
}): JSX.Element {
    if (props.signedIn) {
        console.log(props.pfpURL);
        return <div>
            {
                props.pfpURL !== null ?
                    (<img src={props.pfpURL} alt="Profile" className="TeacherHomepage-pfp"/>)
                : null
            }
            <GoogleLogout
                clientId="1037281959356-1lomvo3e69p8j305qge2hfjrshk90vvh.apps.googleusercontent.com"
                onLogoutSuccess={props.onLogoutSuccess}
                onFailure={props.onLogoutFailure}
                render={props =>
                    <Button variant="outline-primary" onClick={props.onClick} disabled={props.disabled}>Log Out</Button>
                }
            />
        </div>
    } else {
        return (
            <GoogleLogin
                clientId="1037281959356-1lomvo3e69p8j305qge2hfjrshk90vvh.apps.googleusercontent.com"
                onSuccess={props.onSuccess}
                onFailure={props.onLogoutFailure}
                render={props =>
                    <Button variant="outline-primary" onClick={props.onClick} disabled={props.disabled}>Sign In</Button>
                }
            />
        );
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
