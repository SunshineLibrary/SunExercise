jQuery(function($){
	var total_first = {
		"req_id": 201,
		"req_name": "第一次请求－用户－对话－所有科目－第一个科目的所有课程",
		"user": {
			"id": "uid1",
			"name": "小明"
		},
		"dialogs": [
			{
				"type": 0,
				"count": 3,
				"array": [
					{
						"body": "%s, 你做对了，真棒！"
					},
					{
						"body": "%s,太厉害了！"
					},
					{
						"body": "%s,干得好！"
					}
				]
			},
			{
				"type": 1,
				"count": 1,
				"array": [
					{
						"body": "%s, 做错啦，请认真复习！"
					}
				]
			}
		],
		"subjects": [
			{
				"id": "subject1",
				"name": "数学",
				"new_account": 0
			},
			{
				"id": "subject2",
				"name": "语文",
				"new_account": 1
			}
		],
		"subject_id": "subject1",
		"lessons": [
			{
				"id": "l1",
				"name": "有理数的四则运算",
				"time": "1353412800",
				"user_progress": "",
				"user_result": "",
				"stage_count": 2,
				"stage_percentage": 0
			},
			{
				"id": "l2",
				"name": "有理数的四则运算重点总结",
				"time": "1353412799",
				"user_progress": "stage1",
				"user_result": "",
				"stage_count": 2,
				"stage_percentage": 0.5
			}
		]
	}
	
	var subject_item = {
		"req_id": 211,
		"req_name": "请求某个科目",
		"subject_id": "subject1",
		"lessons": [
			{
				"id": "l1",
				"name": "有理数的四则运算",
				"time": "1353412800",
				"user_progress": "",
				"user_result": "",
				"stage_count": 2,
				"stage_percentage": 0
			},
			{
				"id": "l2",
				"name": "有理数的四则运算重点总结",
				"time": "1353412799",
				"user_progress": "stage1",
				"user_result": "",
				"stage_count": 2,
				"stage_percentage": 0.5
			}
		]
	}


	var quiz_str = {
		
		"req_id": 401, 
		"stage_id": "stage1", // 请求阶段ID
		"activity_id": "activity13", // 请求活动ID
		"activities": [ // 阶段下所有活动
			{
				"id": "activity11", // 活动ID
				"section_id": "section1", // 活动所属环节
				"seq": 0, // 顺序，默认按此排列
				"type": 7, // 活动类型
				"name": "加减运算综合的诊断", // 活动名
				"user_progress": "done" // 活动阶段，是跳过还是完成
			},
			{
				"id": "activity12",
				"section_id": "section1",
				"seq": 1,
				"type": 2,
				"name": "加减运算综合视频讲解",
				"user_progress": "done"
			},
			{
				"id": "activity13",
				"section_id": "section1",
				"seq": 2,
				"type": 4,
				"name": "加减运算综合练习题",
				"user_progress": "problem2"
			},
			{
				"id": "activity21",
				"section_id": "section1",
				"seq": 0,
				"type": 7,
				"name": "乘除运算诊断",
				"user_progress": ""
			}
		],
		"activity": {  // 以下请参考411
			"id": "activity13",
			"section_id": "section1",
			"seq": 2,
			"type": 4,
			"name": "加减运算综合练习题",
			"body": "请看视频",
			"weight": 1,
			"jump_condition": [
				{
					"type": 101,
					"to_activity_id": "activity12",
					"high": 100,
					"low": 60
				}
			],
			"user_progress": "problem2",
			"user_duration": "",
			"user_correct": 0,
			"user_score": 0,
			"files": [
				{
					"id": "file1",
					"type": "video",
					"path": "file:\\\\sdcard\\video1.mp4"
				}
			]
		},
		"problems": [
			{
				"id": "problem131",
				"seq": 0,
				"type": 0,
				"body": "下列式子中属于单项式的是",
				"analysis": "",
				"user_duration": 0,
				"user_correct": 0,
				"choices": [
					{
						"id": "choice1",
						"seq": 0,
						"body": "a + 1",
						"answer": 1,
						"user_choice": 0
					},
					{
						"id": "choice2",
						"seq": 1,
						"body": "x = 4",
						"answer": 0,
						"user_choice": 0
					},
					{
						"id": "choice3",
						"seq": 2,
						"body": "axy",
						"answer": 0,
						"user_choice": 0
					}
				],
				"files": [
					{
						"id": "file1",
						"type": "pic",
						"path": "file:\\\\sdcard\\pic.jpeg"
					}
				]
			},
			{
				"id": "problem132",
				"seq": 1,
				"type": 0,
				"body": "下列式子中属于单项式的是",
				"analysis": "",
				"user_duration": 0,
				"user_correct": 0,
				"choices": [
					{
						"id": "choice1",
						"seq": 0,
						"body": "a + 1",
						"answer": 0,
						"user_choice": 0
					},
					{
						"id": "choice2",
						"seq": 1,
						"body": "x = 4",
						"answer": 1,
						"user_choice": 0
					},
					{
						"id": "choice3",
						"seq": 2,
						"body": "axy",
						"answer": 0,
						"user_choice": 0
					}
				],
				"files": [
					{
						"id": "file1",
						"type": "pic",
						"path": "file:\\\\sdcard\\pic.jpeg"
					}
				]
			},
			{
				"id": "problem133",
				"seq": 2,
				"type": 0,
				"body": "下列式子中属于单项式的是",
				"analysis": "",
				"user_duration": 0,
				"user_correct": 0,
				"choices": [
					{
						"id": "choice1",
						"seq": 0,
						"body": "a + 1",
						"answer": 0,
						"user_choice": 0
					},
					{
						"id": "choice2",
						"seq": 1,
						"body": "x = 4",
						"answer": 1,
						"user_choice": 0
					},
					{
						"id": "choice3",
						"seq": 2,
						"body": "axy",
						"answer": 1,
						"user_choice": 0
					}
				],
				"files": [
					{
						"id": "file1",
						"type": "pic",
						"path": "file:\\\\sdcard\\pic.jpeg"
					}
				]
			}
		]
	}

	
	var result_str = {
			// 请求某个活动 诊断，视频或者练习
			"req_id": 411, // 请求ID
			"activity_id": "activity13", // 请求的活动ID
			"activity": { // 返回该活动信息
				"id": "activity13", // 活动ID
				"section_id": "section1", // 活动所属环节
				"seq": 2, // 环节中的次序
				"type": 7,  // 活动类型 0为纯文本 1为音频 2为视频 3为图集 4为测试 5为网页 6为pdf 7为诊断
				"name": "加减运算综合练习题", // 活动名
				"body": "请看视频", // 活动描述，纯文本内容
				"weight": 1, // 活动权重，目前还未使用
				"jump_condition": [ // 跳转条件
					{//差
						"type": 100, // 条件类型，可根据user_correct或者user_score进行跳转
						"to_activity_id": "0~40，activity10", // 跳转到活动ID
						"high": 40, // 上线值
						"low": 0 // 下限值
					},
					{//较差
						"type": 101, // 条件类型，可根据user_correct或者user_score进行跳转
						"to_activity_id": "40~60，activity12", // 跳转到活动ID
						"high": 60, // 上线值
						"low": 40 // 下限值
					},
					{//好
						"type": 102, // 条件类型，可根据user_correct或者user_score进行跳转
						"to_activity_id": "60~80，activity13", // 跳转到活动ID
						"high": 80, // 上线值
						"low": 60 // 下限值
					},
					{//很好
						"type": 103, // 条件类型，可根据user_correct或者user_score进行跳转
						"to_activity_id": "80~100，activity14", // 跳转到活动ID
						"high": 100, // 上线值
						"low": 80 // 下限值
					}
				],
				"user_progress": "problem2", // 活动当前进度，类型为4 7时使用
				"user_duration": "", // 用户停留时间，只在第一次做的时候上报
				"user_correct": 4, // 用户做对题数，可能是float，存在多项填空
				"user_score": 75, // 用户得分
				"files": [ // 视频，音频，pdf，html文件，图片文件，本项为数组可以多个
					{
						"id": "file1", // 文件ID
						"type": "video",  // 文件类型
						"path": "file:\\\\sdcard\\video1.mp4" // 文件路径
					}
				]
			},
			"problems": [ // 活动内所有题目，类型为4 7时使用
				{
					"id": "problem131", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 1, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem132", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 0, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem133", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 1, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem134", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 1, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem135", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 0, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem136", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 1, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem137", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 1, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				},
				{
					"id": "problem138", // 题目id
					"seq": 0, // 题目展示顺序，默认按此排列
					"type": 0, // 题目类型 0为单填，1为单选，2为多选，3为多项填空
					"body": "下列式子中属于单项式的是", // 题干内容
					"analysis": "", // 解答
					"user_duration": 0, // 用户停留时间
					"user_correct": 0, // 用户是否做对
					"choices": [ // 选项或者填空
						{
							"id": "choice1", // id
							"seq": 0, // 次序
							"body": "a + 1", // 内容
							"answer": 1, // 是否应该选或者答案是什么
							"user_choice": 1 // 用户是否选或者填了什么
						},
						{
							"id": "choice2",
							"seq": 1,
							"body": "x = 4",
							"answer": 0,
							"user_choice": 0
						},
						{
							"id": "choice1",
							"seq": 2,
							"body": "axy",
							"answer": 0,
							"user_choice": 0
						}
					],
					"files": [ // 题目中的一些文件，比如几何题
						{
							"id": "file1",
							"type": "pic",
							"path": "file:\\\\sdcard\\pic.jpeg"
						}
					]
				}
			]
		}
	
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
	       	
   var stages =
   {301:[
           
           {
               "id": "stage2",
       "seq": 2,
       "type": 2,
       "user_percentage": 0,
       "user_progress": ""
   },{
       "id": "stage1",
       "seq": 1,
       "type": 1,
       "user_percentage": 0.5,
       "user_progress": "section1"
   }
],302:[
         {
             "id": "stage1",
             "seq": 1,
             "type": 1,
             "user_percentage": 0.5,
             "user_progress": "done"
         },
         {
             "id": "stage2",
             "seq": 2,
             "type": 2,
             "user_percentage": 0,
             "user_progress": ""
         },
         {
              "id": "stage3",
              "seq": 3,
               "type": 3,
               "name": "高级",
               "body": "这个测试中有三个阶段",
               "user_percentage": 0,
               "user_progress": ""
           }
      ]}

	
	Sun = {
		fetchSubjects: function(method) {
            eval(method)(JSON.stringify(total_first));
            alert(android.requestJson(null));
		},
	
//        fetchSubjects: function(method) {
//            eval(method)(JSON.stringify(subjects));
//        },
        
        fetchLessons: function(method) {
            eval(method)(JSON.stringify(subject_item));
        },

		fetchProblems : function(method,id){
			eval(method)(JSON.stringify(quiz_str));
		},
		
		fetchResults: function(method) {
			eval(method)(JSON.stringify(result_str));
		},
		
		fetchProblem: function(method, seq) {
            eval(method)(JSON.stringify(problems[seq-1]));
        },
        
        fetchStages: function(method, id){
            eval(method)(JSON.stringify(stages[id]));
        },
        
		fetchDialogs: function(method, seq) {
            eval(method)(JSON.stringify(dialogs));
        }
	}
});

