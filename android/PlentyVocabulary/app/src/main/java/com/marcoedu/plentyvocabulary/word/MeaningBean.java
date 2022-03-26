package com.marcoedu.plentyvocabulary.word;

import java.util.ArrayList;
import java.util.List;

/*

[
    {
        "word": "abandoned",
        "phonetic": "ÉËband(É)nd",
        "phonetics": [
            {
                "text": "ÉËband(É)nd",
                "audio": "//ssl.gstatic.com/dictionary/static/sounds/20200429/abandoned--_gb_1.mp3"
            }
        ],
        "meanings": [
            {
                "partOfSpeech": "adjective",
                "definitions": [
                    {
                        "definition": "having been deserted or left.",
                        "example": "an abandoned car",
                        "synonyms": [
                            "deserted",
                            "forsaken",
                            "cast aside/off",
                            "jilted",
                            "stranded",
                            "rejected",
                            "dumped",
                            "ditched",
                            "unused",
                            "disused",
                            "neglected",
                            "idle",
                            "unoccupied",
                            "uninhabited",
                            "empty"
                        ],
                        "antonyms": [ ]
                    },
                    {
                        "definition": "unrestrained; uninhibited.",
                        "example": "a wild, abandoned dance",
                        "synonyms": [
                            "uninhibited",
                            "reckless",
                            "unrestrained",
                            "unruly",
                            "wild",
                            "unbridled",
                            "impulsive",
                            "impetuous",
                            "immoderate",
                            "wanton"
                        ],
                        "antonyms": [ ]
                    }
                ]
            }
        ]
    },
    {
        "word": "abandon",
        "phonetic": "ÉËband(É)n",
        "phonetics": [
            {
                "text": "ÉËband(É)n",
                "audio": "//ssl.gstatic.com/dictionary/static/sounds/20200429/abandon--_gb_1.mp3"
            }
        ],
        "origin": "late Middle English: from Old French abandoner, from a- (from Latin ad âto, atâ) + bandon âcontrolâ (related to ban1). The original sense was âbring under controlâ, later âgive in to the control of, surrender toâ (abandon (sense 3 of the verb)).",
        "meanings": [
            {
                "partOfSpeech": "verb",
                "definitions": [
                    {
                        "definition": "cease to support or look after (someone); desert.",
                        "example": "her natural mother had abandoned her at an early age",
                        "synonyms": [
                            "desert",
                            "leave",
                            "leave high and dry",
                            "turn one's back on",
                            "cast aside",
                            "break (up) with",
                            "jilt",
                            "strand",
                            "leave stranded",
                            "leave in the lurch",
                            "throw over",
                            "run/walk out on",
                            "dump",
                            "ditch",
                            "give someone the push",
                            "give someone the big E",
                            "bin off",
                            "forsake"
                        ],
                        "antonyms": [
                            "stick by"
                        ]
                    },
                    {
                        "definition": "give up completely (a practice or a course of action).",
                        "example": "he had clearly abandoned all pretence of trying to succeed",
                        "synonyms": [
                            "renounce",
                            "relinquish",
                            "dispense with",
                            "forswear",
                            "disclaim",
                            "disown",
                            "disavow",
                            "discard",
                            "wash one's hands of",
                            "give up",
                            "drop",
                            "do away with",
                            "jettison",
                            "ditch",
                            "scrap",
                            "scrub",
                            "axe",
                            "junk",
                            "stop",
                            "cease",
                            "forgo",
                            "desist from",
                            "have done with",
                            "abjure",
                            "abstain from",
                            "discontinue",
                            "break off",
                            "refrain from",
                            "set/lay aside",
                            "cut out",
                            "kick",
                            "jack in",
                            "pack in",
                            "quit"
                        ],
                        "antonyms": [
                            "keep",
                            "claim",
                            "continue",
                            "take up"
                        ]
                    },
                    {
                        "definition": "allow oneself to indulge in (a desire or impulse).",
                        "example": "they abandoned themselves to despair",
                        "synonyms": [
                            "indulge in",
                            "give way to",
                            "give oneself up to",
                            "yield to",
                            "lose oneself to/in"
                        ],
                        "antonyms": [
                            "control oneself"
                        ]
                    }
                ]
            }
        ]
    }
]

 */

public class MeaningBean {

    public String word;
    public String phonetic;
    public String origin;
    public List<Mean> meanings;

    public static class Mean {
        public String partOfSpeech;  //noun, verb, or ...
        public List<Definition> definitions;
    }

    public static class Definition {
        public String definition;
        public String example;
        public List<String> synonyms;
        public List<String> antonyms;

        public String partOfSpeech;
    }

    public void postOfParse() {
        if(meanings != null) {
            for(Mean mean : meanings) {
                String pos = mean.partOfSpeech;
                if(mean.definitions != null) {
                    for(Definition def : mean.definitions) {
                        def.partOfSpeech = pos;
                    }
                }
            }
        }
    }

    public List<Definition> getDefList() {
        ArrayList<Definition> defList = new ArrayList<>();
        if(meanings != null) {
            for(Mean mean : meanings) {
                if(mean.definitions != null) {
                    for(Definition def : mean.definitions) {
                        defList.add(def);
                    }
                }
            }
        }
        return defList;
    }

    public static String toStr(MeaningBean[] list) {
        String ret = "";
        StringBuilder sb  = new StringBuilder();
        sb.append("toStr() list:"+list+", \n");
        if(list != null) {

            sb.append("mean list size:"+list.length+", \n");
            for(MeaningBean bean : list) {
                sb.append("word:"+bean.word+", \n");
                sb.append("phonetic:"+bean.phonetic+", \n");
                sb.append("origin:"+bean.origin+", \n");
                sb.append("meanings size:"+(bean.meanings != null ? bean.meanings.size():0)+"\n");
                if(bean.meanings != null) {
                    sb.append("meanings list:[\n");
                    for(Mean mean : bean.meanings) {
                        sb.append("\n--meaning ===============================================\n");
                        sb.append("--meaning, partOfSpeech:"+mean.partOfSpeech+", \n");
                        sb.append("--meaning, definitions size:"+(mean.definitions != null ? mean.definitions.size() : 0)+"\n");
                        if(mean.definitions != null) {
                            sb.append("--++definition list:[\n");
                            for(Definition def : mean.definitions) {
                                sb.append("--++definition ++++++++++++++++++++++++++++++++\n");
                                sb.append("--++definition def:"+def.definition+"\n");
                                sb.append("--++definition example:"+def.example+"\n");
                                sb.append("--++definition synonyms:["+def.synonyms+"],\n");
                                sb.append("--++definition antonyms:["+def.antonyms+"]\n");
                                sb.append("--++definition --------------------------------\n");
                            }
                            sb.append("--++]definition list\n");
                        }
                    }
                    sb.append("] meanings list\n");
                }

            }
        }
        ret = sb.toString();
        return ret;
    }
}


