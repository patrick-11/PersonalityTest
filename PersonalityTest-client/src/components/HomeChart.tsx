import React from "react"

import { Container, Col, Row } from "react-bootstrap"
import { Result } from "../hooks/reducer/ResultsReducer"


interface HomeChartInterface {
  results: Array<Result>
}

const HomeChart = (props: HomeChartInterface) => {
  const getInfo = () => {
      if(props.results.length > 0) {
        let avgScores: Array<number> = []
        props.results.forEach((result: Result) => avgScores.push(result.avgScore))
        const avgScore = avgScores.reduce((a, b) => (a + b)) / props.results.length
        const sdScore = Math.sqrt(avgScores.map((x) => (Math.pow(x - avgScore, 2))).reduce((a, b) => (a + b)) / props.results.length)
        return {"testCount": props.results.length, "avgScore": Number(avgScore.toFixed(2)), "sdScore": Number(sdScore.toFixed(2))}
      }
      return {"testCount": 0, "avgScore": 0, "sdScore": 0}
  }

  const info = getInfo()

  return (
    <Container>
      <Row>
        <Col>
          <h3>Conducted Tests: {info.testCount}</h3>
        </Col>
        <Col className="text-center">
          <h3>Average Score: {info.avgScore}</h3>
        </Col>
        <Col className="text-end">
          <h3>Standard Deviation: {info.sdScore}</h3>
        </Col>
      </Row>
    </Container>
  )
}

export default HomeChart