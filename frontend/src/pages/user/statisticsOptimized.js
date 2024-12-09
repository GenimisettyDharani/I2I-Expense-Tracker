import React, { useEffect, useState } from 'react';
import UserService from '../../services/UserService'; // Assuming this is the correct path
import AuthService from '../../services/AuthService'; // Assuming this is the correct path
import getMonths from './dashboard'; // Importing the existing getMonths function

const useOptimizeDataFetch = (months) => {
    const [data, setData] = useState([]);
    const [isLoading, setIsLoading] = useState(true);
    const [isError, setIsError] = useState(false);
    const [cache, setCache] = useState({}); // Cache to store fetched data

    const fetchData = async () => {
        const email = AuthService.getCurrentUser().email;

        if (cache[email]) {
            // If data is in cache, set it directly
            setData(cache[email]);
            setIsLoading(false);
            return;
        }

        try {
            const response = await UserService.getMonthlySummary(email);
            if (response.data.status === "SUCCESS") {
                const finalData = generateData(response.data.response);
                setData(finalData);
                setCache((prevCache) => ({ ...prevCache, [email]: finalData }));
            } else {
                setIsError(true);
            }
        } catch (error) {
            setIsError(true);
            console.error("Error fetching data:", error);
        } finally {
            setIsLoading(false);
        }
    };

    const generateData = (fetchedData) => {
        return months.map(({ id, monthName }) => {
            const monthData = fetchedData.find((item) => item.month === id);
            return {
                id,
                monthName,
                totalIncome: monthData ? monthData.total_income : 0,
                totalExpense: monthData ? monthData.total_expense : 0,
            };
        });
    };

    useEffect(() => {
        fetchData();
    }, [months]);

    return [data, isLoading, isError];
};

export default useOptimizeDataFetch;
