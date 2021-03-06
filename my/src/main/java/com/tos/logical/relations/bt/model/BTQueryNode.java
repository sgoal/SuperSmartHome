package com.tos.logical.relations.bt.model;

import com.tos.module.devices.Device;
import com.tos.module.devices.Device.QueryRes;
import com.tos.utils.Properties;

public class BTQueryNode extends BTLeaf {
	protected Device actor;
	private String queryId;

	@Override
	public void init(Object nData, Object nActor) {
		this.actor = (Device) nActor;
		this.properties = (Properties) nData;
	}

	@Override
	public int process() {
		String cmd = properties.cmd_id;
		if (queryId == null) {
			queryId = actor.query(cmd);
			if (queryId == null) {
				return BTExecution.CONST_EXEC_FAILURE;
			}
			QueryRes queryRes = actor.queryResult(queryId);
			if (queryRes != null && queryRes.hasResult) {
				String res = queryRes.result;
				String expRes = properties.expect_result;
				if (properties.result_type.equals("float")) {
					float fres = Float.parseFloat(res);
					float efres = Float.parseFloat(properties.expect_result);
					switch (properties.operator) {
					case "lt":
						if (fres < efres)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "le":
						if (fres < efres)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "eq":
						if (fres == efres)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "ne":
						if (fres != efres)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "gt":
						if (fres > efres)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "ge":
						if (fres >= efres)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;

					default:
						break;
					}
				} else {
					switch (properties.operator) {
					case "lt":
						if (res.compareToIgnoreCase(expRes) < 0)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "le":
						if (res.compareToIgnoreCase(expRes) <= 0)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "eq":
						if (res.equalsIgnoreCase(expRes))
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "ne":
						if (!res.equalsIgnoreCase(expRes))
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "gt":
						if (res.compareToIgnoreCase(expRes) > 0)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;
					case "ge":
						if (res.compareToIgnoreCase(expRes) >= 0)
							return BTExecution.CONST_EXEC_SUCCESS;
						break;

					default:
						break;
					}
				}

				return BTExecution.CONST_EXEC_FAILURE;
			}
			return BTExecution.CONST_EXEC_RUNNING;
		}

		return BTExecution.CONST_EXEC_SUCCESS;
	}

	public static class QueryEntity {
		public String cmd;
		public String expectResult;
		/**
		 * 支持eq,ne,lt,le,ge,gt,表示结果于期望值相比;lt表示 结果<expectResult
		 */
		public String operator = "eq";
		/**
		 * 表示结果的数据类型，支持,str,float两种
		 */
		public String typeResult = "float";
	}
}
