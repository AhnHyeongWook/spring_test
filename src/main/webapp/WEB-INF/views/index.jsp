<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-2.1.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#imgInp").on('change', function() {
			readURL(this);
		});
	});

	function readURL(input) {
		if (input.files && input.files[0]) {
			var image = input.files[0];
			var reader = new FileReader();
			var formData = new FormData();
			formData.append("image", image);

			console.log(image);

			$.ajax({
				url : "thumbnail",
				method : "POST",
				data : formData,
				dataType: "text",
				contentType: false,
				processData: false,
				success : function(result) {
					alert(result);
				}
			});

			reader.onload = function(e) {
				$('#img1').attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
<html>
<head>
<title>testImage</title>
</head>
<body>
	<div>data test</div>
	<div>${mapList}</div>
	<div>
		<input id="imgInp" type="file">
		<div>origin</div>
		<div>
			<img id="img1">
		</div>
		<hr>
		<div>resize</div>
		<div>
			<img id="img2">
		</div>
	</div>
</body>
</html>