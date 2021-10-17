export default class User {
	name: string
	gender: string
	age: number
	answers: Array<number>
	results: Array<number>
	avgScore: number

	constructor(name: string, gender: string, age: number, answers: Array<number>) {
		this.name = name
		this.gender = gender
		this.age = age
		this.answers = answers
		this.results = []
		this.avgScore = 0
	}

	calculateResults = () => {
		let temp = []
		const reverse = (value: number) => {return (8 - value)}
		temp[0] = (Number(this.answers[0]) + reverse(this.answers[5]))/2
		temp[1] = (Number(this.answers[6]) + reverse(this.answers[1]))/2
		temp[2] = (Number(this.answers[2]) + reverse(this.answers[7]))/2
		temp[3] = (Number(this.answers[8]) + reverse(this.answers[3]))/2
		temp[4] = (Number(this.answers[4]) + reverse(this.answers[9]))/2
		this.results = temp
	}

	calculateAvgScore = () => {
		let temp = 0
		this.results.forEach((result) => {temp += result})
		this.avgScore = (temp/this.results.length)
	}
}