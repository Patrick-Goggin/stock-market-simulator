/* implementation heavily influenced by http://bl.ocks.org/1166403 */

//var data = [3, 6, 2, 7, 5, 2, 0, 3, 8, 9, 2, 5, 9, 3, 6, 3, 6, 2, 7, 5, 2, 1, 3, 8, 9, 2, 5, 9, 2, 7];


function NewGraph(msg){
		// define dimensions of graph
		var m = [30, 0, 80, 40]; // margins
		var w = 700 - m[1] - m[3]; // width
		var h = 400 - m[0] - m[2]; // height
		var padding = (100/1.7);
    var arraySize = msg.length - 1;
    var data = msg;
    var firstX = data[0].quoteDate;
    var lastX = data[arraySize].quoteDate;
    var highest = data[0].l;
    var lowest = data[0].l;
    var current;

    for (var i = 0; i < arraySize + 1; i++) {
        current = data[i].l;
        if (current > highest) {
            highest = current;
        }
        if (current < lowest) {
            lowest = current;
        }
    }

    var minDate = new Date(data[0].quoteDate),
        maxDate = new Date(data[data.length - 1].quoteDate);

// define the x scale
    var x = d3.time.scale()
            .domain([minDate, maxDate])    // values between for month of january
            .range([0, w]);   // map these the the chart width = total width minus padding at both sides




// define the y scale
    var y = d3.scale.linear()
            .domain([lowest, highest])
            .range([h, 0]);

// define the line
        var line = d3.svg.line()
            .x(function (d) {
                return x(new Date(d.quoteDate));
            })
            .y(function (d) {
                return y(d.l);
            });

// Add an SVG element with the desired dimensions and margin.
			var graph = d3.select("#graph").append("svg:svg")
			      .attr("width", w + m[1] + m[3])
			      .attr("height", h + m[0] + m[2])
			    .append("svg:g")
			      .attr("transform", "translate(" + m[3] + "," + m[0] + ")");

// create xAxis
var xAxis = d3.svg.axis().scale(x).tickSize(-h).tickSubdivide(true).orient("bottom");
// Add the x-axis.
			graph.append("svg:g")
			      .attr("class", "x axis xaxis")
			      .attr("transform", "translate(0," + h + ")")
			      .call(xAxis);

graph.selectAll(".xaxis text")  // select all the text elements for the xaxis
            .attr("transform", function (d) {
                return "translate(" + (this.getBBox().height * -2) + "," + (this.getBBox().height+15) + ")rotate(-45)";
            });

// create left yAxis
            var yAxisLeft = d3.svg.axis().scale(y).ticks(20).tickSize(-w).orient("left");
			// Add the y-axis to the left
			graph.append("svg:g")
			      .attr("class", "y axis")
			      .attr("transform", "translate(0,0)")
			      .call(yAxisLeft);

  			// Add the line by appending an svg:path element with the data line we created above
			// do this AFTER the axes above so that the line is above the tick-lines
  		//	graph.append("svg:path").attr("d", line(data));
    graph.append('svg:path')
            .attr('d', line(data))
            .attr('stroke', 'green')
            .attr('stroke-width', 1)
            .attr('fill', 'none');

            var titleSize = measure("Title of Diagram", "title");
                    m[0] = titleSize.height + 20;

            graph.append("text")
            		.attr("x", w / 2 )
                    .attr("y", -titleSize.height/2 - 10)
                    .attr("class", "title")
                    .style("text-anchor", "middle")
                    .text("Share Price / Time");

            }

//SimpleGraph(data);
