<html>
<body>

	<p>Create New Group</p>

	<script type="text/javascript">
		function myFunction() {
			var x;

			var name = prompt("Please Enter Group Name", "NK SHARED GROUP");

			if (name) {
				x = name;
				window.location = "/contacts/createGroup.do?groupName=" + x;
			} else {
				myFunction();
			}
		}
		myFunction();
	</script>