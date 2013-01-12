jQuery(function($) {
    var subjects = [
        { id: 1, name: "语文"},
        { id: 2, name: "数学" },
        { id: 3, name: "英语" }
    ]

    var lessons = [
        [
            { id: 1, name: "Lit 1"},
            { id: 2, name: "Lit 2"}
        ],
        [
            { id: 1, name: "Math 1"},
            { id: 2, name: "Math 2"},
            { id: 3, name: "Math 3"}
        ],
        []
    ]

    Sun = {
        fetchSubjects: function(method) {
                           eval(method)(JSON.stringify(subjects));
                       },
        fetchLessons: function(method, id) {
                          eval(method)(JSON.stringify(lessons[id-1]));
                      }
    }
});

