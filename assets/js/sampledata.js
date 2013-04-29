/**
 * User: fxp
 * Date: 13-3-28
 * Time: PM12:39
 */

sample_schema = {
    subjects_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["subjects"],
        "properties": {
            "subjects": {
                "description": "All subjects for this user",
                "type": "array",
                "id": "subjects",
                "items": {
                    "type": "object",
                    "id": "0",
                    "required": ["id", "name"],
                    "properties": {
                        "id": {
                            "description": "The unique identifier for a subject",
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        }
                    }
                }
            }
        }
    },
    subject_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["id", "name"],
        "properties": {
            "id": {
                "description": "The unique identifier for a subject",
                "type": "string"
            },
            "name": {
                "type": "string"
            },
            "lessons": {
                "type": "array",
                "id": "lessons",
                "items": {
                    "type": "object",
                    "required": ["id", "name", "subject_id", "time"],
                    "properties": {
                        "id": {
                            "description": "The unique identifier for a lesson",
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "seq": {
                            "description": "The sequence of the lesson in this subject",
                            "type": "string",
                            "pattern": "^[0-9]+"
                        },
                        "subject_id": {
                            "type": "string"
                        },
                        "time": {
                            "pattern": "^[0-9]{4}-[0-9]{2}-[0-9]{2}",
                            "type": "string"
                        },
                        "download_finish": {
                            "type": "string"
                        }
                    }
                }
            }
        }
    },
    lesson_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["id", "name", "subject_id", "time"],
        "properties": {
            "id": {
                "type": "string"
            },
            "name": {
                "type": "string"
            },
            "seq": {
                "type": "string",
                "pattern": "^[0-9]+"
            },
            "stages": {
                "type": "array",
                "items": {
                    "type": "object",
                    "required": ["id", "lesson_id", "type"],
                    "properties": {
                        "id": {
                            "type": "string"
                        },
                        "lesson_id": {
                            "type": "string"
                        },
                        "seq": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        },
                        "type": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        }
                    }
                }
            },
            "subject_id": {
                "type": "string"
            },
            "time": {
                "type": "string",
                "pattern": "^[0-9]{4}-[0-9]{2}-[0-9]{2}"
            },
            "download_finish": {
                "type": "string"
            }
        }
    },
    stage_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["id", "lesson_id", "type"],
        "properties": {
            "id": {
                "type": "string"
            },
            "lesson_id": {
                "type": "string"
            },
            "sections": {
                "type": "array",
                "items": {
                    "type": "object",
                    "required": ["id", "name", "stage_id"],
                    "properties": {
                        "id": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "seq": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        },
                        "stage_id": {
                            "type": "string"
                        }
                    }
                }
            },
            "seq": {
                "type": "string",
                "pattern": "^[0-9]+"
            },
            "type": {
                "type": "string",
                "pattern": "^[0-9]+"
            }
        }
    },
    section_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["id", "name", "stage_id"],
        "properties": {
            "activities": {
                "type": "array",
                "items": {
                    "type": "object",
                    "required": ["body", "id", "name", "section_id", "type"],
                    "properties": {
                        "body": {
                            "type": "string"
                        },
                        "id": {
                            "type": "string"
                        },
                        "jump_condition": {
                            "type": "string"
                        },
                        "media_id": {
                            "type": "string"
                        },
                        "name": {
                            "type": "string"
                        },
                        "section_id": {
                            "type": "string"
                        },
                        "seq": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        },
                        "type": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        }
                    }
                }
            },
            "id": {
                "type": "string"
            },
            "name": {
                "type": "string"
            },
            "seq": {
                "type": "string",
                "pattern": "^[0-9]+"
            },
            "stage_id": {
                "type": "string"
            }
        }
    },
    activity_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["body", "id", "name"],
        "properties": {
            "body": {
                "type": "string"
            },
            "id": {
                "type": "string"
            },
            "jump_condition": {
                "type": "string"
            },
            "media_id": {
                "type": "string"
            },
            "name": {
                "type": "string"
            },
            "problems": {
                "type": "array",
                "items": {
                    "type": "object",
                    "required": ["activity_id", "body", "id", "type"],
                    "properties": {
                        "activity_id": {
                            "type": "string"
                        },
                        "analysis": {
                            "type": "string"
                        },
                        "body": {
                            "type": "string"
                        },
                        "id": {
                            "type": "string"
                        },
                        "media_id": {
                            "type": "string"
                        },
                        "seq": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        },
                        "type": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        }
                    }
                }
            },
            "section_id": {
                "type": "string"
            },
            "seq": {
                "type": "string",
                "pattern": "^[0-9]+"
            },
            "type": {
                "type": "string",
                "pattern": "^[0-9]+"
            }
        }
    },
    problem_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-04/schema",
        "required": ["activity_id", "body", "id", "type"],
        "properties": {
            "activity_id": {
                "type": "string"
            },
            "analysis": {
                "type": "string"
            },
            "body": {
                "type": "string"
            },
            "choices": {
                "type": "array",
                "items": {
                    "type": "object",
                    "required": ["answer", "id", "problem_id"],
                    "properties": {
                        "answer": {
                            "type": "string"
                        },
                        "display_text": {
                            "type": "string"
                        },
                        "id": {
                            "type": "string"
                        },
                        "problem_id": {
                            "type": "string"
                        },
                        "seq": {
                            "type": "string",
                            "pattern": "^[0-9]+"
                        }
                    }
                }


            },
            "id": {
                "type": "string"
            },
            "media_id": {
                "type": "string"
            },
            "seq": {
                "type": "string",
                "pattern": "^[0-9]+"
            },
            "type": {
                "type": "string"
            }
        }
    },
    jump_condition_schema: {
        "type": "object",
        "$schema": "http://json-schema.org/draft-03/schema",
        "required": ["to_activity_id"],
        "properties": {
            "condition": {
                "type": "object",
                "required": ["type", "min", "max"],
                "properties": {
                    "max": {
                        "type": "number"
                    },
                    "min": {
                        "type": "number"
                    },
                    "type": {
                        "type": "string"
                    }
                }
            },
            "to_activity_id": {
                "type": "string"
            }
        }
    }

}

sample_data = {
    subjects: {
        "subjects": [
            {"id": "su1", "name": "数学"},
            {"id": "su2", "name": "英语"}
        ]
    },
    subject: {"id": "su1", "name": "数学", "lessons": [
        {"id": "l1", "subject_id": "su1", "name": "有理数的四则运算（加减）", "seq": "0", "time": "2012-12-20", "download_finish": "1"},
        {"id": "l2", "subject_id": "su1", "name": "有理数的四则运算（乘除）", "seq": "1", "time": "2012-12-24", "download_finish": "none"},
        {"id": "l3", "subject_id": "su1", "name": "绝对值的概念", "seq": "2", "time": "2013-01-01", "download_finish": "waiting"},
        {"id": "l5", "subject_id": "su1", "name": "呵呵的概念", "seq": "3", "time": "2013-01-10", "download_finish": "0.6432"},
        {"id": "l4", "subject_id": "su1", "name": "", "seq": "4", "time": "2013-02-01", "download_finish": "downloading"}
    ]},
    lesson: {"id": "l1", "subject_id": "su1", "name": "有理数的四则运算（加减）", "seq": "0", "time": "2012-12-20", "download_finish": "1", "stages": [
        {"id": "st1", "lesson_id": "l1", "seq": "0", "type": "1"},
        {"id": "st2", "lesson_id": "l1", "seq": "1", "type": "2"}
    ]},
    stage: {"id": "st1", "lesson_id": "l1", "seq": "0", "type": "1", "sections": [
        {"id": "se1", "stage_id": "st1", "seq": "0", "name": "同号加减"},
        {"id": "se2", "stage_id": "st1", "seq": "1", "name": "异号加减"},
        {"id": "se3", "stage_id": "st1", "seq": "2", "name": "加减计算综合"}
    ]},
    section: {"id": "se1", "stage_id": "st1", "seq": "0", "name": "同号加减", "activities": [
        {"id": "a1", "section_id": "se1", "seq": "0", "type": "7", "name": "同号加减", "body": "", "jump_condition": "[{\"condition\":\"CorrectCount(3,3)\",\"to_activity_id\":\"a4\"}]", "media_id": ""},
        {"id": "a2", "section_id": "se1", "seq": "1", "type": "2", "name": "同号加减视频讲解", "body": "", "jump_condition": "", "media_id": "3"},
        {"id": "a3", "section_id": "se1", "seq": "2", "type": "4", "name": "同号加减－练习", "body": "", "jump_condition": "", "media_id": ""}
    ]},
    activity: {"id": "a1", "section_id": "se1", "seq": "0", "type": "7", "name": "同号加减", "body": "", "jump_condition": "[{\"condition\":\"CorrectCount(3,3)\",\"to_activity_id\":\"a4\"}]", "media_id": "", "problems": [
        {"id": "p1", "activity_id": "a1", "seq": "0", "type": "0", "body": "这是一道单选题啊单选题的题干", "analysis": "", "media_id": "22411"},
        {"id": "p2", "activity_id": "a1", "seq": "1", "type": "1", "body": "这是一道多选题啊多选题的题干", "analysis": "", "media_id": "22412"}
    ]},
    problem: {"id": "p1", "activity_id": "a1", "seq": "0", "type": "0", "body": "这是一道单选题啊单选题的题干", "analysis": "", "media_id": "22411", "choices": [
        {"id": "c1", "problem_id": "p1", "seq": "0", "display_text": "错误选项一", "answer": "no"},
        {"id": "c2", "problem_id": "p1", "seq": "1", "display_text": "错误选项二", "answer": "no"},
        {"id": "c3", "problem_id": "p1", "seq": "2", "display_text": "正确选项三", "answer": "yes"},
        {"id": "c4", "problem_id": "p1", "seq": "3", "display_text": "错误选项四", "answer": "no"}
    ]},
    jump_condition: [
        {
            "to_activity_id": "a9",
            "condition": {
                "type": "CorrectCount",
                "min": 1, "max": 3
            }
        }
    ],
    userdata: {
        "current": ""
    }
}

jQuery(function () {
    json_validation = function () {
        console.log("===start sample json schema validation===")
        console.log(tv4.validate(sample_data.subjects, sample_schema.subjects_schema) + "\tsubjects")
        console.log(tv4.validate(sample_data.subject, sample_schema.subject_schema) + "\tsubject")
        console.log(tv4.validate(sample_data.lesson, sample_schema.lesson_schema) + "\tlesson")
        console.log(tv4.validate(sample_data.stage, sample_schema.stage_schema) + "\tstage")
        console.log(tv4.validate(sample_data.section, sample_schema.section_schema) + "\tsection")
        console.log(tv4.validate(sample_data.activity, sample_schema.activity_schema) + "\tactivity")
        console.log(tv4.validate(sample_data.problem, sample_schema.problem_schema) + "\tproblem")
        console.log(tv4.validate(sample_data.jump_condition, sample_schema.jump_condition_schema) + "\tjump_condition")
        console.log("===end sample json schema validation===")
    }
})
