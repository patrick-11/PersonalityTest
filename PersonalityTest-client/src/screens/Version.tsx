import React from "react"

import { Container, Card } from "react-bootstrap"


const Version = () => {
  return (
    <Container>
      <Card>
        <Card.Header>v2.0.0</Card.Header>

        <Card.Body>
          <Card.Title>New Features</Card.Title>

          <Card.Subtitle>Screen: Averages</Card.Subtitle>
          <ul>
            <li>Filter for average score</li>
            <li>Reset filter and reload/update result list</li>
          </ul>

          <Card.Subtitle>Screen: Users</Card.Subtitle>
          <ul>
            <li>See results of user</li>
            <li>Update and delete user</li>
            <li>Filter for name, gender, and age</li>
            <li>Reset filter and reload/update user list</li>
          </ul>

          <Card.Subtitle>Screen: Results</Card.Subtitle>
          <ul>
            <li>Inspect result of user</li>
            <li>Update and delete result</li>
            <li>Filter for name, gender, age, and score</li>
            <li>Reset filter and reload/update result list</li>
          </ul>

          <Card.Subtitle>General</Card.Subtitle>
          <ul>
            <li>Add version screen to keep track of features and improvements</li>
            <li>Add Toast box to display success or error</li>
            <li>Introduction of Docker for easy deployment</li>
          </ul>

          <hr/>
          <Card.Title>Improvements</Card.Title>
          <ul>
            <li>Implementation of createContext hook: enables global state</li>
            <li>Implementation of useReducer hook: for custom state logic</li>
            <li>Implementation of useCallback hook: avoid too many re-renders</li>
            <li>Implementation of axios: improved HTTP requests</li>
          </ul>

          <hr/>
          <Card.Title>Future Improvements</Card.Title>
          <ul>
            <li>Add filter for date</li>
            <li>Add night mode</li>
            <li>Introduce password to log in</li>
          </ul>
        </Card.Body>
      </Card>
    </Container>
  )
}

export default Version