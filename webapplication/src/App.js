import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import {Col, Container, Row} from "react-bootstrap";
import NavigationBar from "./components/NavigationBar";
import Footer from "./components/Footer";
import Train from "./components/Train";
import Wagon from "./components/Wagon";

export default function App() {

    const marginBlock = {
        marginTop: "20px",
        marginBlockEnd: "45px"
    };

    return (
        <Router>
            <NavigationBar/>
            <Container>
                <Row>
                    <Switch>
                        <Col lg={12} style={marginBlock}>
                            <Route path={"/train"} exact component={Train}/>
                            <Route path={"/about"} exact component={Train}/>
                            <Route exact path="/:trainType/:id" component={Wagon} />
                        </Col>
                    </Switch>
                </Row>
            </Container>
            <Footer/>
        </Router>
    );
}
