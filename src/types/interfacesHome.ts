export interface InfoChartProps {
	info: Info;
}

type Info = {
	testCount: number;
	avgScore: number;
	sdScore: number;
}