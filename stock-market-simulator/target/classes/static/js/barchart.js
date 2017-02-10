//
//		// Create Global Variable
//		var dataset;
//
//		dataset = [
//		{CustomerCount: Math.floor(Math.random()*250),State: "FL"},
//		{CustomerCount: 25,State: "GA"},
//		{CustomerCount: 10,State: "NY"},
//		{CustomerCount: 200,State: "TX"},
//		{CustomerCount: 1400,State: "ALL"}
//		]
//
//			// Call function
//			Graph(dataset);


		// Create function
		function Graph(input) {

		// Declare Variables
		var margin = {top: 60, right: 60, bottom: 60, left:120},
		w = 600 - margin.left - margin.right,
		h = 400 - margin.top - margin.bottom;

        // update the margin based on the title size
        var titleSize = measure("Title of Diagram", "title");
        margin.top = titleSize.height + 20;

		//Create X Scale for bar graph
		var xScale = d3.scale.ordinal()
							 .domain(input.map(function (d){ return d.t;}))
							 .rangeRoundBands([0, w], 0.05);

		//Create Y Scale for bar graph
		var yScale = d3.scale.linear()
					   .domain([0,d3.max(input, function(d) { return d.l; })])
					   .range([h, 0]);

		//Create X Axis
		var xAxis = d3.svg.axis()
					  .scale(xScale)
					  .orient("bottom");

		//Create Y Axis
		var yAxis = d3.svg.axis()
					  .scale(yScale)
					  .orient('left');

		//Create SVG element
		var svg = d3.select("#barChart")
					.append("svg")
					.attr("width", w + margin.left + margin.right)
					.attr("height", h + margin.top + margin.bottom)
					.append('g')
					.attr("transform", "translate(" + margin.left + "," + margin.top + ")");

		//Create X axis
		svg.append("g")
		   .attr("class", "xaxis")
		   .attr("transform", "translate(0," + h + ")")
		   .call(xAxis);

		//Create Title
		svg.append("text")
		.attr("x", w / 2 )
        .attr("y", -titleSize.height/2 + 10)
        .attr("class", "title")
        .style("text-anchor", "middle")
        .text("Your Portfolio");

		//Create X axis label
		svg.append("text")
		.attr("x", w / 2 )
        .attr("y",  h + margin.bottom)
        .attr("class", "axLabel")
        //.attr("class", "xaxis")
        .style("text-anchor", "middle")
        .text("Stock");

		//Create Y axis
		svg.append("g")
		   .attr("class", "yaxis")
		   .call(yAxis);

		//Create Y axis label
		svg.append("text")
        .attr("transform", "rotate(-90)")
        .attr("class", "axLabel")
        .attr("y", 0-margin.left)
        .attr("x",0 - (h / 2))
        .attr("dy", "1em")
        //.attr("class", "yaxis")
        .style("text-anchor", "middle")
        .text("Share Price");

		//Add rectangles
		svg.selectAll(".bar")
		   .data(input)
		   .enter()
		   .append("rect")
		   .attr("class", "bar")
           .style("fill", "green")
		   .attr("x", function(d) { return xScale(d.t); })
		   .attr("y", function(d) { return yScale(d.l) })
		   .attr("width", xScale.rangeBand()) //returns rangeRoundBands width
		   .attr("height", function(d) { return h - yScale(d.l) });


		}; // end Graph function


// create a dummy element, apply the appropriate classes,
// and then measure the element
function measure(text, classname) {
    if(!text || text.length === 0) return {height: 0, width: 0};

    var container = d3.select('body').append('svg').attr('class', classname);
    container.append('text').attr({x: -1000, y: -1000}).text(text);

    var bbox = container.node().getBBox();
    container.remove();

    return {height: bbox.height, width: bbox.width};
}