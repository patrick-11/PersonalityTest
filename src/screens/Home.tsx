import React, { useEffect, useState } from "react"


import { Container, Row, Col, Card, Button } from "react-bootstrap"
import { useHistory } from "react-router"

import InfoChart from "../components/homeInfo"

import { UsersProps } from "../types/interfacesRouter"


const Home = (props: UsersProps) => {
	let history = useHistory()
	const redirectAction = (name: string) => {history.push("/" + name)}

	const [info, setInfo] = useState({"testCount": 0, "avgScore": 0, "sdScore": 0})


	useEffect(() => {
		const users = props.users
		let avgScores: Array<number> = []
		users.forEach((user) => avgScores.push(user.avgScore))
		const avgScore = avgScores.reduce((a, b) => (a + b)) / users.length
		const sdScore = Math.sqrt(avgScores.map((x) => (Math.pow(x - avgScore, 2))).reduce((a, b) => (a + b)) / users.length)
		setInfo({"testCount": users.length, "avgScore": Number(avgScore.toFixed(2)), "sdScore": Number(sdScore.toFixed(2))})
	}, [props.users])


	return (
		<Container>
			<hr/>
			<Row>
				<Col>
					<div className="text-center">
						<h1>Personality Test</h1>
						<h4>Our goal is to find out the personality with ten questions. This test will evaluate the user in five aspects: Extraversion, agreeableness, conscientiousness, emotional stability, and openness to experiences.</h4>
					</div>
				</Col>
			</Row>

			<hr/>
			<InfoChart
				info = {info}
			/>
			<hr/>
			
			<Container>
				<Row>
					<Col md={4}>
						<Card>
							<Card.Header>
								<h4 className="text-center">Test</h4>
							</Card.Header>
							<Card.Body>
								<p>Take the Ten-Item Personality Inventory-(TIPI) test and receive your result.</p>
								<div className="d-grid gap-2">
									<Button
										size="lg"
										onClick={() => redirectAction("Test")}
									>
										Take Test
									</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
					<Col md={4}>
						<Card>
							<Card.Header>
								<h4 className="text-center">Results</h4>
							</Card.Header>
							<Card.Body>
								<p>See the average result of all participants which have concluded this test.</p>
								<div className="d-grid gap-2">
									<Button
										size="lg"
										onClick={() => redirectAction("Results")}
									>
										Check Results
									</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
					<Col md={4}>
						<Card>
							<Card.Header>
								<h4 className="text-center">Search</h4>
							</Card.Header>
							<Card.Body>
								<p>Search the individual result of participants which have concluded this test.</p>
								<div className="d-grid gap-2">
									<Button
										size="lg"
										onClick={() => redirectAction("Search")}
									>
										Search Results
									</Button>
								</div>
							</Card.Body>
						</Card>
					</Col>
				</Row>
			</Container>
			<hr/>
		</Container>
	)
}

export default Home