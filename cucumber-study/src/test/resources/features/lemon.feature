Feature: 使用chrome浏览器访问百度搜索柠檬班论坛
  Scenario Outline: 百度搜索testingpai
    Given open
    When input <query>
    Then show <title>
    Examples:
      | query      | title             |
      | testingpai | 测试派 - 软件测试工程师的心灵社区 |