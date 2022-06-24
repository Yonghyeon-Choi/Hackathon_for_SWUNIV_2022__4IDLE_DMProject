import React, { useState, useEffect } from "react";

import MatchService from "../../services/match.service";
import DefaultDataService from "../../services/defaultData.service";
import "../GlobalStyles.css";

import * as am4core from "@amcharts/amcharts4/core";
import * as am4charts from "@amcharts/amcharts4/charts";
import am4themes_animated from "@amcharts/amcharts4/themes/animated";
import * as am4plugins_sliceGrouper from "@amcharts/amcharts4/plugins/sliceGrouper";
am4core.useTheme(am4themes_animated);

const MatchList = (props) => {
  const [matches, setMatches] = useState([]);
  const [searchWord, setSearchWord] = useState("");

  const initialDataState = {
    id: null,
    diseases: [],
    symptoms: []
  };
  const [data, setData] = useState(initialDataState);

  const retrieveData = () => {
    DefaultDataService.getAll()
        .then((response) => {
          setData(response.data);
        })
        .catch((e) => {
          console.log(e);
        });

  };

  const retrieveMatches = () => {
    MatchService.getAll()
        .then((response) => {
          setMatches(response.data);
        })
        .catch((e) => {
          console.log(e);
        });
  };

    const findByWord = () => {
        MatchService.findByWord(searchWord)
            .then(response => {
                setMatches(response.data);
                console.log(response.data);
            })
            .catch(e=>{
                console.log(e);
            });
    };

  // eslint-disable-next-line
  useEffect(() => {
    retrieveMatches();
  }, []);

  useEffect(() => {
    retrieveData();
  }, []);

  const onChangeSearchWord = (e) => {
    e.preventDefault();
    const searchWord = e.target.value;
    setSearchWord(searchWord);
  };

  const openMatch = (id) => {
    props.history.push("/match/" + id);
  };

  const makeData = () => {
      let symptoms = [...data.symptoms];
      let diseases = [...data.diseases];

      for (let i=0; i<symptoms.length; i++){
          symptoms[i].weight = Number(symptoms[i].weight)
          delete symptoms[i].symptomid
          delete symptoms[i].id
      }
      for (let i=0; i<symptoms.length; i++){
          for (let j=0; j<diseases.length; j++){
              let inSymptoms = diseases[j].symptoms;
              for (let k=0; k<inSymptoms.length; k++){
                  if (symptoms[i].symptomname === inSymptoms[k].symptomname){
                      symptoms[i].weight += 1
                  }
              }
          }
      }

      return symptoms;
  };

    let chart = am4core.create("chartdiv", am4charts.PieChart);
    chart.data = makeData().sort((a, b) => b.weight - a.weight).slice(0,30);

    // Add and configure Series
    let pieSeries = chart.series.push(new am4charts.PieSeries());
    pieSeries.dataFields.value = "weight";
    pieSeries.dataFields.category = "symptomname";

    let grouper = pieSeries.plugins.push(new am4plugins_sliceGrouper.SliceGrouper());
    grouper.threshold = 1;
    grouper.groupName = "Other";
    grouper.clickBehavior = "zoom";

    let title = chart.titles.create();
    title.text = "상위 30개 증상 비율";
    title.fontSize = 25;
    title.marginBottom = 20;

    let label = chart.chartContainer.createChild(am4core.Label);
    label.text = "단위 : 개";
    label.align = "center";


  return (
      <div>
          <div id="chartdiv" style={{ width: "100%", height: "500px" }}>

          </div>
          <div className="card">
                <div style={{width: "100%"}}>{/*className="col-md-8"*/}
                    <div className="input-group">
                        <table width="100%">
                            <tbody>
                            <tr>
                                <td width="25%">
                                    <div className="input-group">
                                        <input
                                            type="text"
                                            className="form-control"
                                            placeholder=""
                                            value={searchWord}
                                            onChange={onChangeSearchWord}
                                        />
                                        <div className="input-group-append">
                                            <button
                                                className="btn btn-outline-secondary form-control"
                                                type="button"
                                                onClick={findByWord}
                                            >
                                                검색
                                            </button>
                                        </div>
                                    </div>
                                </td>
                                <td width="55%"/>
                                <td width="10%"/>
                                <td width="5%">
                                    <h6>{matches.length}</h6>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                <table className="table table-bordered">
                  <thead>
                    <tr className={"nonBorder"}>
                      <td width="15%" className={"nonBorder"}>작성자</td>
                      <td width="15%" className={"nonBorder"}>제목</td>
                      <td width="15%" className={"nonBorder"}>내용</td>
                      <td width="15%" className={"nonBorder"}>증상</td>
                      <td width="15%" className={"nonBorder"}>예측 질병</td>
                      <td width="15%" className={"nonBorder"}>진단 결과</td>
                      <td width="10%" className={"nonBorder"}/>
                    </tr>
                    {/*<tr></tr>*/}
                  </thead>
                  <tbody>
                  {matches && matches.map((match, index) => (
                      <tr key={index}>
                        <td>
                          {match.writer}
                        </td>
                        <td>
                          {match.title}
                        </td>
                        <td>
                          {match.description}
                        </td>
                        <td>
                          {match.symptoms.map(
                              (symptom)=>(
                                  <div key={symptom.symptomid}><i><b>{"#"+symptom.symptomname}</b></i></div>
                              )
                          )}
                        </td>
                        <td>
                          {match.predict[0]+", "+match.predict[1]+", "+match.predict[2]}
                        </td>
                        <td>
                          {match.result}
                        </td>
                        <td>
                          <div>
                            <button
                                type="button"
                                className="editBtnStyle"
                                onClick={() => openMatch(match.id)}>
                              >>
                            </button>
                          </div>
                        </td>
                      </tr>
                  ))}
                  </tbody>
                </table>
              </div>
          </div>
      </div>
  );
};

export default MatchList;
