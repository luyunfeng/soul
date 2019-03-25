# 数组转集合

```
Arrays.stream(activityIds).boxed().collect(Collectors.toList());
```
```
String[] arrays = new String[]{"a", "b", "c"};
List<String> listStrings = Stream.of(arrays).collect(Collectors.toList());

```
```

String[] arrays = new String[]{"a", "b", "c"};
List<String> listStrings = Arrays.asList(arrays);
```
```
Arrays.newArrList("1","2")

```

```
int [] brandIds 
List<Intger> = Arrays.stream(brandIds).boxed().collect(toList());
```

# 集合转数组
## String 类型
```
String[] ss = listStrings.stream().toArray(String[]::new);
```
```
String[] sss = listStrings.toArray(new String[listStrings.size()]);

```
## int 之类
```
activityIdList.stream().mapToInt(Integer::valueOf).toArray()
```

# List 转Map
```
 public static Map<Integer, PlayMethod> mapByPlayMethodId(List<PlayMethod> playMethods) {
        if (CollectionUtils.isNotEmpty(playMethods)) {
            return playMethods.stream().collect(Collectors.toMap(x -> x.playMethodId, x -> x));
        }
        return Maps.newHashMap();
    }
```
