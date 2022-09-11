import React, { useContext, useReducer, useEffect } from "react"

import { Container, Row, Col, Card, Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { ToastContext } from "../hooks/context/toastContext"
import { resultsReducer } from "../hooks/reducer/resultsReducer"
import { resultsActions } from "../hooks/reducer/actions/resultsActions"
import { resultsAPI } from "../data/results"
import HomeChart from "../components/HomeChart"


const Home = () => {
	let navigate = useNavigate()
	const toastContext  = useContext(ToastContext)
	const [results, dispatch] = useReducer(resultsReducer, [])

	useEffect(() => {
		const getResults = () => {
			resultsAPI.getResults()
				.then(response => dispatch(resultsActions.setResults(response)))
				.catch(error => toastContext.onToastAdd({show: true, title: "Error", message: error, color: "danger"}))
		}
		getResults()
	}, [toastContext])


	const navigateAction = (name: string) => {navigate("/" + name)}

	return (
		<>
			<Row>
				<Col>
					<div className="text-center">
						<h1>Personality Test</h1>
						<h4>Our goal is to find out the personality with ten questions. This test will evaluate the user in five aspects: Extraversion, agreeableness, conscientiousness, emotional stability, and openness to experiences.</h4>
					</div>
				</Col>
			</Row>

			<hr/>
			<HomeChart results = {results}/>
			<hr/>

			<Container>
				<Row>
					<Col>
						<Card>
							<Card.Header>
								<h4 className="text-center">Test</h4>
							</Card.Header>
							<Card.Body>
								<Card.Text>Take the Ten-Item Personality Inventory-(TIPI) test and see your result.</Card.Text>
								<div className="d-grid gap-2">
									<Button size="lg" onClick={() => navigateAction("test")}>Take Test</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
					<Col>
						<Card>
							<Card.Header>
								<h4 className="text-center">Averages</h4>
							</Card.Header>
							<Card.Body>
								<Card.Text>See the average results of all participants which have concluded the TIPI test.</Card.Text>
								<div className="d-grid gap-2">
									<Button size="lg" onClick={() => navigateAction("averages")}>See Averages</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
					<Col>
						<Card>
							<Card.Header>
								<h4 className="text-center">Users</h4>
							</Card.Header>
							<Card.Body>
								<Card.Text>See all the participants information which are registered on this site.</Card.Text>
								<div className="d-grid gap-2">
									<Button size="lg" onClick={() => navigateAction("users")}>See Users</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
					<Col>
						<Card>
							<Card.Header>
								<h4 className="text-center">Results</h4>
							</Card.Header>
							<Card.Body>
								<Card.Text>See the results of all participants which have concluded the TIPI test.</Card.Text>
								<div className="d-grid gap-2">
									<Button size="lg" onClick={() => navigateAction("results")}>See Results</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
				</Row>
			</Container>
		</>
	)
}

export default Home