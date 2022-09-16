import React from "react"

import { Button, Row, Col, Stack, ButtonGroup, ToggleButton } from "react-bootstrap"
import { UserInterface } from "../hooks/context/UserContext"


interface TestQuestionsInterface {
  user: UserInterface,
  onAnswer: (answer: number) => void,
  onCancel: () => void,
  onSubmit: () => void,
  onIncrement: () => void,
  onDecrement: () => void,
}

const TestQuestions = (props: TestQuestionsInterface) => {
  const questions = [
    "Extraverted, enthusiastic.",
    "Critical, quarrelsome.",
    "Dependable, self-disciplined.",
    "Anxious, easily upset.",
    "Open to new experiences, complex.",
    "Reserved, quiet.",
    "Sympathetic, warm.",
    "Disorganized, careless.",
    "Calm, emotionally stable.",
    "Conventional, uncreative."
  ]

  const answersOptions = [
    {name: "Disagree strongly", value: 1},
    {name: "Disagree moderately", value: 2},
    {name: "Disagree a little", value: 3},
    {name: "Neither agree nor disagree", value: 4},
    {name: "Agree a little", value: 5},
    {name: "Agree moderately", value: 6},
    {name: "Agree strongly", value: 7},
  ]

  return (
    <>
      <Row>
        <Col>
          <div className="text-center">
            <h4>I see myself as: {questions[props.user.questionIndex]}</h4>
          </div>
        </Col>
      </Row>
      <br/>
      <Row>
        <Col className="text-center">
          <ButtonGroup>
            {answersOptions.map((answer, index) => (
              <ToggleButton
                key={index}
                id={`radio-${index}`}
                type="radio"
                variant="outline-primary"
                name="radio"
                value={answer.value}
                checked={props.user.answers[props.user.questionIndex] === answer.value}
                onChange={(event) => props.onAnswer(parseInt(event.currentTarget.value))}
              >
                {answer.name}
              </ToggleButton>
            ))}
          </ButtonGroup>
        </Col>
      </Row>
      <hr/>
      <Row>
        <Col className="text-center">
          <Stack direction="horizontal" gap={2} className="justify-content-center">
            <Button variant="secondary" onClick={() => props.onCancel()}>Cancel</Button>
            <Button variant="primary" disabled={props.user.questionIndex === 0} onClick={() => props.onDecrement()}>Back</Button>
            {props.user.questionIndex < 9 ?
              <Button variant="primary" disabled={typeof props.user.answers[props.user.questionIndex] === "undefined"} onClick={() => props.onIncrement()}>Next</Button>
              :
              <Button variant="success" disabled={typeof props.user.answers[props.user.questionIndex] === "undefined"} onClick={() => props.onSubmit()}>Submit</Button>
            }
          </Stack>
        </Col>
      </Row>
    </>
  )
}

export default TestQuestions