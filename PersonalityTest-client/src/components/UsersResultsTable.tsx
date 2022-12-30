import React, { useState } from "react"

import { Table, InputGroup, Form, Stack, Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { Result } from "../hooks/reducer/ResultsReducer"


interface UsersResultsTableInterface {
  results: Array<Result>,
  userId: string,
  getResults: (userId: string) => void,
  deleteResult: (id: string) => void
 }

const UsersResultsTable = (props: UsersResultsTableInterface) => {
  let navigate = useNavigate()
  const filterDefault = {name: "", gender: "", ageMin: "5", ageMax: "120", avgScoreMin: "1", avgScoreMax: "7"}
  const [filter, setFilter] = useState(filterDefault)


  const filterResults = (event: any) => {
    event.persist()
    setFilter((filter) => ({...filter, [event.target.name]: event.target.value}))
  }

  const filterReset = () => {setFilter(filterDefault)}

  const results = filter !== filterDefault ?
    props.results.filter((result: Result) => {
      return result.avgScore >= parseInt(filter.avgScoreMin) && 
      result.avgScore <= parseInt(filter.avgScoreMax)
    }) : props.results

  return (
    <Table striped bordered hover>
      <thead>
        <tr>
          <th>#</th>
          <th>Completed</th>
          <th colSpan={4}>Score</th>
          <th className="text-center">Action</th>
        </tr>
        <tr className="align-middle">
          <td>{results.length}</td>
          <td></td>
          <td className="text-center">{filter.avgScoreMin}</td>
          <td>
            <InputGroup>
              <Form.Range id="avgScoreMin" name="avgScoreMin" step="1" value={filter.avgScoreMin} min="1" max={filter.avgScoreMax} onChange={filterResults}/>
            </InputGroup>
          </td>
          <td>
              <InputGroup>
              <Form.Range id="avgScoreMax" name="avgScoreMax" step="1" value={filter.avgScoreMax} min={filter.avgScoreMin} max="7" onChange={filterResults}/>
            </InputGroup>
          </td>
          <td className="text-center">{filter.avgScoreMax}</td>

          <td>
            <Stack direction="horizontal" gap={1} className="justify-content-center">
              <Button size="sm" variant="outline-danger" onClick={() => filterReset()}><i className="bi bi-x-lg"/></Button>
              <Button size="sm" variant="outline-success" onClick={() => props.getResults(props.userId)}><i className="bi bi-arrow-clockwise"/></Button>
            </Stack>
          </td>
        </tr>
      </thead>
      <tbody>
        {results.length === 0 ?
          <tr className="text-center">
            <td colSpan={7}>No results available!</td>
          </tr>
          :
          results.map((result: any, index: number) => {
            return (
              <tr key={index}>
                <td>{index + 1}</td>
                <td>{result.completed}</td>
                <td colSpan={4}>{result.avgScore}</td>
                <td className="text-center">
                  <Stack direction="horizontal" gap={1} className="justify-content-center">
                    <Button size="sm" variant="outline-info" onClick={() => navigate("/results/" + result.id)}><i className="bi bi-eye"/></Button>
                    <Button size="sm" variant="outline-success" onClick={() => navigate("/results/" + result.id + "/update")}><i className="bi bi-pencil"/></Button>
                    <Button size="sm" variant="outline-danger" onClick={() => props.deleteResult(result.id)}><i className="bi bi-trash3"/></Button>
                  </Stack>
                </td>
              </tr>
            )
          })}
      </tbody>
    </Table>
  )
}

export default UsersResultsTable