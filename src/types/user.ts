export default class User {
	name: string
	gender: "Male" | "Female"
	age: number
	answers: Array<number>
	results: Array<number>
	avgScore: number

	constructor(name: string, gender: "Male" | "Female", age: number, answers: Array<number>) {
		this.name = name
		this.gender = gender
		this.age = age
		this.answers = answers
		this.results = this.calculateResults(this.answers)
		this.avgScore = this.getAvgScore(this.results)
	}

	calculateResults = (answers: Array<number>) => {
		let temp = []
		const reverse = (value: number) => {return (8 - value)}
		temp[0] = (Number(answers[0]) + reverse(answers[5]))/2;
		temp[1] = (Number(answers[6]) + reverse(answers[1]))/2;
		temp[2] = (Number(answers[2]) + reverse(answers[7]))/2;
		temp[3] = (Number(answers[8]) + reverse(answers[3]))/2;
		temp[4] = (Number(answers[4]) + reverse(answers[9]))/2;
		return temp
	}

	getAvgScore = (results: Array<number>) => {
		let temp = 0
		results.forEach((result) => {temp += result})
		return (temp/results.length)
	}
}