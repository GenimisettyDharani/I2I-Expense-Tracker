import { useEffect, useState } from 'react';

/**
 * Custom hook for optimized data fetching.
 * This hook handles loading state and error management for data fetching.
 * 
 * @param {function} fetchData - The data fetching function to be called.
 * @returns {object} An object containing data, loading state, and error information.
 */
export const useOptimizeData = (fetchData) => {
    const [data, setData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDataWrapper = async () => {
            try {
                setLoading(true);
                const result = await fetchData();
                setData(result);
            } catch (err) {
                // Log the error for debugging
                console.error("Data fetching error: ", err);
                setError(err);
            } finally {
                setLoading(false);
            }
        };

        fetchDataWrapper();
    }, [fetchData]);

    return { data, loading, error };
};
