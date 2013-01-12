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


    Sun = {
        fetchSubjects: function(method) {
            eval(method)(JSON.stringify(subjects));
        },
        fetchLessons: function(method, id) {
            eval(method)(JSON.stringify(lessons[id-1]));
        },
    }
});

