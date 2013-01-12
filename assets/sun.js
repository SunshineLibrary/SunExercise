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
            { id: 7, date: "2013-01-20", title:"13年的灰太狼和喜洋洋1"},
			{ id: 8, date: "2013-02-20", title:"13年的灰太狼和喜洋洋2"}
        ],
        [
            { id: 1, date: "2012-02-23", title:"单项式分析"},
            { id: 2, date: "2012-11-14", title:"单项式分析"},
            { id: 3, date: "2012-12-12", title:"单项式分析"},
            { id: 4, date: "2012-10-05", title:"单项式分析"},
            { id: 5, date: "2013-02-14", title:"13年的多元一次方程1"},
            { id: 6, date: "2013-09-15", title:"13年的多元一次方程2"},
			{ id: 7, date: "2013-10-16", title:"13年的多元一次方程3"}
        ],
        []
    ]


    Sun = {
        fetchSubjects: function(method) {
            eval(method)(JSON.stringify(subjects));
        },
        fetchLessons: function(method, id) {
            eval(method)(JSON.stringify(lessons[id-1]));
        },
    }
});

