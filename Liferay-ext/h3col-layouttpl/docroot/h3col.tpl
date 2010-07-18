<div class="columns-1-3" id="main-content" role="main">
	<table class="portlet-layout">
		<tr>
			<td class="portlet-column portlet-column-only" id="column-1">
				$processor.processColumn("column-1", "portlet-column-content portlet-column-content-only")
			</td>
		</tr>
	</table>

	<table class="portlet-layout">
	<tr>
		<td class="aui-w33 portlet-column portlet-column-first" id="column-2">
			$processor.processColumn("column-2", "portlet-column-content portlet-column-content-first")
		</td>
		<td class="aui-w33 portlet-column" id="column-3">
			$processor.processColumn("column-3", "portlet-column-content")
		</td>
		<td class="aui-w33 portlet-column portlet-column-last" id="column-4">
			$processor.processColumn("column-4", "portlet-column-content portlet-column-content-last")
		</td>
	</tr>
	</table>
</div>