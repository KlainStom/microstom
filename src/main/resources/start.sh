while true ; do
    java -Xms512M -Xmx2G -jar micro.jar
    exitcode="$?"
    if [ $exitcode -ne "99" ]; then
        echo "
Server exit code: $exitcode"
        break
    fi
    echo "

 === SERVER RESTART ===

"
done