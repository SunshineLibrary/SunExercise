jQuery(function($) {
    var subjects = [
        { id: 1, name: "语文"},
        { id: 2, name: "数学" },
        { id: 3, name: "英语" }
    ]

    var lessons = [
        [
           {week:"星期一",day:23,month:12,title:"小红帽和大灰狼"},
			{week:"星期一",day:23,month:12,title:"小红帽和大灰狼"},
			{week:"星期一",day:23,month:12,title:"小红帽和大灰狼"},
			{week:"星期一",day:23,month:12,title:"小红帽和大灰狼"},
			{week:"星期五",day:16,month:11,title:"灰太狼和喜洋洋"},
			{week:"星期五",day:16,month:11,title:"灰太狼和喜洋洋"}
        ],
        [
            {week:"星期三",day:23,month:12,title:"单项式分析"},
			{week:"星期三",day:23,month:12,title:"单项式分析"},
			{week:"星期三",day:23,month:12,title:"单项式分析"},
			{week:"星期三",day:23,month:12,title:"单项式分析"},
			{week:"星期二",day:16,month:11,title:"多元一次方程"},
			{week:"星期二",day:16,month:11,title:"多元一次方程"}
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

