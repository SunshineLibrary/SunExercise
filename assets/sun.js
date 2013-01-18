jQuery(function($) {
    var subjects = [
        { id: 1, name: "语文"},
        { id: 2, name: "数学" },
        { id: 3, name: "英语" }
    ]

    var lessons = [
        [
            { id: 1, date: "2012-12-23", title:"小红帽和大灰狼"},
            { id: 2, date: "2012-11-14", title:"小红帽和大灰狼"},
            { id: 3, date: "2012-12-12", title:"小红帽和大灰狼"},
            { id: 4, date: "2012-10-05", title:"小红帽和大灰狼"},
            { id: 5, date: "2012-11-06", title:"小红帽和大灰狼"},
            { id: 6, date: "2012-12-01", title:"灰太狼和喜洋洋"},
            { id: 7, date: "2013-01-20", title:"灰太狼和喜洋洋"}
        ],
        [
            { id: 1, date: "2012-12-23", title:"单项式分析"},
            { id: 2, date: "2012-11-14", title:"单项式分析"},
            { id: 3, date: "2012-12-12", title:"单项式分析"},
            { id: 4, date: "2012-10-05", title:"单项式分析"},
            { id: 5, date: "2013-02-14", title:"多元一次方程"},
            { id: 6, date: "2013-03-15", title:"多元一次方程"}
        ],
        []
    ]
	
	var dialogs = [
		{type: 0, count: 3, 
			array: 
			[
				{body: "%s, 你做对了，真棒！"},
				{body: "%s,太厉害了！"},
				{body:"%s,干得好！"}
			]
		},
		{type: 1, count: 1, 
			array: 
			[
				{body: "%s, 做错啦，请认真复习！"}
			]
		}
	]
	
	var problems = [
		
			{ id:"problem1", seq: 1, body: "下列式子中属于单项式的是", type: 0, 
				choices:
				[
					{ id: "choice1", seq: 1, body: "a + 1", answer: 1},
					{ id: "choice2", seq: 2, body: "x = 4", answer: 0},
					{ id: "choice3", seq: 3, body: "abc", answer: 0}
				]
			},
			{ id:"problem2", seq: 2, body: "下列式子中不属于单项式的是", type: 1,
				choices:
				[
					{ id: "choice1", seq: 1, body: "a + 1", answer: 0},
					{ id: "choice2", seq: 2, body: "x - 4", answer: 0},
					{ id: "choice3", seq: 3, body: "abc", answer: 1}
				]
			}
	]
	
	

    Sun = {
        fetchSubjects: function(method) {
            eval(method)(JSON.stringify(subjects));
        },
        fetchLessons: function(method, id) {
            eval(method)(JSON.stringify(lessons[id-1]));
        },
		fetchProblem: function(method, seq) {
            eval(method)(JSON.stringify(problems[seq-1]));
        },
		fetchDialogs: function(method, seq) {
            eval(method)(JSON.stringify(dialogs));
        },
    }
	
	
});

