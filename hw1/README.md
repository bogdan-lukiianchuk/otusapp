http://arch.homework/health (response: {"status":"OK"})
http://arch.homework/otusapp/bogdan/health (response: {"status":"OK"})
http://arch.homework/otusapp/bogdan/ (response: {"Hello!"})
http://arch.homework/otusapp/bogdan/<studentName> (response: {"Hello `<studentName>`!"})

Применение манифестов
```
kubectl apply -f .
```

curl в миникубе
```
curl -H 'Host: arch.homework' http://<ingress ip>/health
curl -H 'Host: arch.homework' http://<ingress ip>/otusapp/bogdan/
```
