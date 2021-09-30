import { Profile } from "./interfacesRouter";

export interface TestProps {
	inTest: boolean;
	profile: Profile;
	answers: Array<string>;
	results: Array<number>;
	resultShow: boolean;
	onInTestChange: (state: boolean) => void;
	onAnswersChange: (answers: Array<string>) => void;
	onResultShow: (state: boolean) => void;
	onRegisterShow: (state: boolean) => void;
}

export interface TestStartProps {
	profile: Profile;
	onInTestChange: (state: boolean) => void;
	onRegisterShow: (state: boolean) => void;
}

export interface TestQuestionsProps {
	answers: Array<string>;
	onInTestChange: (state: boolean) => void;
	onAnswersChange: (answers: Array<string>) => void;
	onResultShow: (state: boolean) => void;
}