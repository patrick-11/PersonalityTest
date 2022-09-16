import React, { useState } from "react"

import { Table, InputGroup, Form, Stack, Button } from "react-bootstrap"
import { Result } from "../hooks/reducer/ResultsReducer"
import RadarChart from "./RadarChart"


interface AveragesChartInterface {
  results: Array<Result>,
  getResults: () => void
}

const AveragesChart = (props: AveragesChartInterface) => {
  const filterDefault = {name: "", gender: "", ageMin: "5", ageMax: "120", avgScoreMin: "1", avgScoreMax: "7"}
  const [filter, setFilter] = useState(filterDefault)


  const filterResults = (event: any) => {
    event.persist()
    setFilter((filter) => ({...filter, [event.target.name]: event.target.value}))
  }

  const filterReset = () => {setFilter(filterDefault)}

  const calculateAvgResults = (allResults: Array<Result>) => {
    let avgResults = [0,0,0,0,0]
    allResults.forEach((result) => {result.results.forEach((score, index) => {avgResults[index] += score})})
    avgResults.forEach((result, index) => {avgResults[index] = result/allResults.length})
    return avgResults
  }

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

  const avgResults = calculateAvgResults(results)

  return (
    <>
      <Table bordered>
        <thead>
          <tr>
            <th>Tests</th>
            <th>Gender</th>
            <th colSpan={4}>Age</th>
            <th colSpan={4}>Score</th>
            <th className="text-center">Action</th>
          </tr>
          <tr className="align-middle">
          <td className="text-center">{results.length}</td>
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
                <Button size="sm" variant="outline-success" onClick={() => props.getResults()}><i className="bi bi-arrow-clockwise"/></Button>
              </Stack>
            </td>
          </tr>
        </thead>
      </Table>
      <hr/>
      <RadarChart result={avgResults}/>
    </>
  )
}

export default AveragesChart