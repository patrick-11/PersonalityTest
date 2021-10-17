import React from "react"


import { Radar } from 'react-chartjs-2'

import { RadarChartProps } from "../types/interfacesRouter"


const RadarChart = (props: RadarChartProps) => {
	return (
		<Radar
			data = {{
				labels: [
					"Extraversion",
					"Agreeableness",
					"Conscientiousness",
					"Emotional Stability",
					"Openness to Experiences"
				],
				datasets: [{
					label: "Result",
					data: props.results,
					fill: true,
					backgroundColor: 'rgba(54, 162, 235, 0.2)',
					borderColor: 'rgb(54, 162, 235)',
					pointBackgroundColor: 'rgb(54, 162, 235)',
					pointBorderColor: '#fff',
					pointHoverBackgroundColor: '#fff',
					pointHoverBorderColor: 'rgb(54, 162, 235)'
				  }]
			}}
		/>
	)
}

export default RadarChart