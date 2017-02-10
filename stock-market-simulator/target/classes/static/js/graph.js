function getDate(d) {
    return new Date(d.jsonDate);
}

function InitChart(msg) {
    var arraySize = msg.length - 1;
    var data = msg;
    var firstX = data[0].quoteDate;
    var lastX = data[arraySize].quoteDate;
    MARGINS = {
        top: 0,
        right: 0,
        bottom: 0,
        left: 0
    };
    var highest = data[0].l;
    var lowest = data[0].l;
    var current;
    for (var i = 0; i < arraySize + 1; i++) {
        //alert(myStringArray[i]);
        //Do something
        current = data[i].l;
        if (current > highest) {
            highest = current;
        }
        if (current < lowest) {
            lowest = current;
        }
    }
    // define the x scale (horizontal)
    var minDate = new Date(data[0].quoteDate),
            maxDate = new Date(data[data.length - 1].quoteDate);
    var xScale = d3.time.scale()
            .domain([minDate, maxDate])    // values between for month of january
            .range([padding, width - padding * 2]);   // map these the the chart width = total width minus padding at both sides
    var width = (700/1.5),
            height = (400/1.4),
            padding = (100/1.7);

    // create an svg container
    var vis = d3.select("#graph-container").
            append("svg:svg")
            .attr("width", width)
            .attr("height", height)
            .attr("id", 'graph');

    // define the y scale  (vertical)
    var yScale = d3.scale.linear()
            .domain([lowest, highest])
            .range([height - padding, padding]);   // map these to the chart height, less padding.
    //REMEMBER: y axis range has the bigger number first because the y value of zero is at the top of chart and increases as you go down.
    // define the x scale (horizontal)
    var mindate = new Date(data[0].quoteDate),
            maxdate = new Date(data[arraySize].quoteDate);
    var xScale = d3.time.scale()
            .domain([mindate, maxdate])    // values between for month of january
            .range([padding, width - padding * 2]);   // map these the the chart width = total width minus padding at both sides
    // define the y axis
    var yAxis = d3.svg.axis()
            .orient("left")
            .scale(yScale);
    // define the y axis
    var xAxis = d3.svg.axis()
            .orient("bottom")
            .scale(xScale);
    // draw y axis with labels and move in from the size by the amount of padding
    vis.append("g")
            .attr("transform", "translate(" + padding + ",0)")
            .attr('class', 'yaxis')
            .call(yAxis);
    // draw x axis with labels and move to the bottom of the chart area
    vis.append("g")
            .attr("class", "xaxis")   // give it a class so it can be used to select only xaxis labels  below
            .attr("stroke-width", 1)
            .attr("transform", "translate(0," + (height - padding) + ")")
            .call(xAxis);
    // now rotate text on x axis
    // solution based on idea here: https://groups.google.com/forum/?fromgroups#!topic/d3-js/heOBPQF3sAY
    // first move the text left so no longer centered on the tick
    // then rotate up to get 45 degrees.



    //THIS IS WHERE YOU CAN MOVE THE X-AXIS LABELS!!!!
    //THIS IS WHERE YOU CAN MOVE THE X-AXIS LABELS!!!!
    //THIS IS WHERE YOU CAN MOVE THE X-AXIS LABELS!!!!
    vis.selectAll(".xaxis text")  // select all the text elements for the xaxis
            .attr("transform", function (d) {
                return "translate(" + (this.getBBox().height * -2) + "," + (this.getBBox().height+15) + ")rotate(-45)";
            });
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////




//    var lineGen = d3.svg.line()
//            .x(function (d) {
//                return xScale(new Date(d.quoteDate));
//            })
//            .y(function (d) {
//                return yScale(d.l);
//            });
//
//    vis.append('svg:path')
//            .attr('d', lineGen(data))
//            .attr('stroke', 'green')
//            .attr('stroke-width', 1)
//            .attr('fill', 'none');

//    var lineGen = d3.svg.line()
//            .x(function (d) {
//                return xScale(new Date(d.quoteDate));
//            })
//            .y(function (d) {
//                return yScale(d.l);
//            })
//            .interpolate("basis");
//    vis.append('svg:path')
//            .attr('d', lineGen(data))
//            .attr('stroke', 'green')
//            .attr('stroke-width', 1)
//            .attr('fill', 'none');
    var lineGen = d3.svg.line()
            .x(function (d) {
                return xScale(new Date(d.quoteDate));
            })
            .y(function (d) {
                return yScale(d.l);
            });
    vis.append('svg:path')
            .attr('d', lineGen(data))
            .attr('stroke', 'green')
            .attr('stroke-width', 1)
            .attr('fill', 'none');
}
InitChart();
