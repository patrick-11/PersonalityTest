import React, { useState } from "react"

import { Table, InputGroup, Form, Stack, Button } from "react-bootstrap"
import { useNavigate } from "react-router-dom"
import { Result } from "../hooks/reducer/ResultsReducer"
import { convertTimestamp } from "../util/Util"


interface ResultsTableInterface {
  results: Array<Result>,
  details: "high" | "low",
  userId?: string,
  getResults: (userId?: string) => void,
  deleteResult: (id: string) => void
 }

const ResultsTable = (props: ResultsTableInterface) => {
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
      return result.user.name.indexOf(filter.name) !== -1 &&
        (filter.gender.length === 0 ?
          result.user.gender === "Male" || result.user.gender === "Female" : result.user.gender === filter.gender
        ) &&
        result.user.age >= parseInt(filter.ageMin) &&
        result.user.age <= parseInt(filter.ageMax) &&
        result.avgScore >= parseInt(filter.avgScoreMin) &&
        result.avgScore <= parseInt(filter.avgScoreMax)
    }) : props.results

  const span = props.details === "high" ? 13 : 7

  return (
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            {props.details === "high" ?
              <>
                <th>Name</th>
                <th>Gender</th>
                <th colSpan={4}>Age</th>
              </>
              :
              null
            }
            <th>Completed</th>
            <th colSpan={4}>Score</th>
            <th className="text-center">Action</th>
          </tr>
          <tr className="align-middle">
            <td></td>
            {props.details === "high" ?
              <>
                <td>
                  <InputGroup>
                    <Form.Control placeholder="Name" type="text" name="name" value={filter.name} onChange={filterResults}/>
                  </InputGroup>
                </td>
                <td className="text-center">
                  <InputGroup>
                    <Form.Select name="gender" value={filter.gender} onChange={filterResults}>
                      <option value="">All</option>
                      <option value="Male">Male</option>
                      <option value="Female">Female</option>
                    </Form.Select>
                  </InputGroup>
                </td>
                <td className="text-center">{filter.ageMin}</td>
                <td>
                  <InputGroup>
                    <Form.Range id="ageMin" name="ageMin" step="1" value={filter.ageMin} min="5" max={filter.ageMax} onChange={filterResults}/>
                  </InputGroup>
                </td>
                <td>
                  <InputGroup>
                    <Form.Range id="ageMax" name="ageMax" step="1" value={filter.ageMax} min={filter.ageMin} max="120" onChange={filterResults}/>
                  </InputGroup>
                </td>
                <td className="text-center">{filter.ageMax}</td>
              </>
              :
              null
            }
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
                <Button size="sm" variant="outline-success" onClick={() => {
                  if(props.userId === undefined) {props.getResults()}
                  else {props.getResults(props.userId)}
                }}><i className="bi bi-arrow-clockwise"/></Button>
              </Stack>
            </td>
          </tr>
        </thead>
        <tbody>
          {results.length === 0 ?
            <tr className="text-center"><td colSpan={span}>No results available!</td></tr>
            :
            results.map((result: any, index: number) => {
              return (
                <tr key={index}>
                  <td>{index + 1}</td>
                  {props.details === "high" ?
                    <>
                      <td>{result.user.name}</td>
                      <td>{result.user.gender}</td>
                      <td colSpan={4}>{result.user.age}</td>
                    </>
                    :
                    null
                  }
                  <td>{convertTimestamp(result.completed)}</td>
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

export default ResultsTable