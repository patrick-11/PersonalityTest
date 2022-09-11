import React from "react"

import { Chart as ChartJS, RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend, } from "chart.js";
import { Radar } from "react-chartjs-2";


ChartJS.register(RadialLinearScale, PointElement, LineElement, Filler, Tooltip, Legend);

interface RadarChartInterface {
	result: Array<number>
}

const RadarChart = (props: RadarChartInterface) => {

	const data = {
		labels: [
			"Extraversion",
			"Agreeableness",
			"Conscientiousness",
			"Emotional Stability",
			"Openness to Experiences"
		],
		datasets: [{
			label: "Result",
			data: props.result,
			backgroundColor: 'rgba(54, 162, 235, 0.2)',
			borderColor: 'rgb(54, 162, 235)',
			borderWidth: 2
		}]
	}

	return (<Radar data={data}/>)
}

export default RadarChart